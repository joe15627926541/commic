package com.luoshunkeji.comic.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoshunkeji.comic.MyWebView;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.TopupAdapter;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.entity.PayResultEntity;
import com.luoshunkeji.comic.entity.payWay;
import com.luoshunkeji.comic.entity.vipEntity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.DensityUtil;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.T;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

/**
 * 金币充值页面
 */
public class TopupActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;
    private RecyclerView recycleView;
    private TopupAdapter adapter;
    private View mHeadView;
    private View mFootView;
    private TextView tvService;
    private TextView tvPasswd;
    private TextView tvVipNumber;
    private TextView tvVipState;
    private TextView tvCoinNumber;
    private TextView tvWechatPay;
    private TextView tvAliPay;
    private vipEntity mVipEntity;
    private int pay_id_alipay;
    private int pay_id_wechat;
    private RelativeLayout dialog;


    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_top_up);
    }

    @Override
    public void initData() {
        getPaymentInfo();
        getPaymentWay();

    }

    private void getPaymentWay() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("payment_channel").byGet(Urls.PAYMENT_CHANNEL, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void getPaymentInfo() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("payment_info").byGet(Urls.PAYMENT_INFO, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvAliPay.setEnabled(true);
        tvWechatPay.setEnabled(true);
    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.title).text("金币充值");
        mq.id(R.id.back).clicked(this);
        mHeadView = LayoutInflater.from(this)
                .inflate(R.layout.item_head_top_up, null);
        mFootView = LayoutInflater.from(this)
                .inflate(R.layout.item_foot_top_up, null);
        tvCoinNumber = (TextView) mHeadView.findViewById(R.id.tvCoinNumber);
        tvCoinNumber.setText(SPUtils.getPrefInt(TopupActivity.this, Pkey.currency, 0) + "");


        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        dialog = (RelativeLayout) findViewById(R.id.dialog);

        tvService = (TextView) mFootView.findViewById(R.id.tvService);
        tvWechatPay = (TextView) mFootView.findViewById(R.id.tvWechatPay);
        tvAliPay = (TextView) mFootView.findViewById(R.id.tvAliPay);
        tvPasswd = (TextView) mHeadView.findViewById(R.id.tvPasswd);
        tvVipState = (TextView) mHeadView.findViewById(R.id.tvVipState);
        tvVipNumber = (TextView) mHeadView.findViewById(R.id.tvVipNumber);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recycleView.setLayoutManager(layoutManager);
        tvPasswd.setText("账号: " + SPUtils.getPrefString(TopupActivity.this, Pkey.username, "") + "/" + "密码: " + SPUtils.getPrefString(TopupActivity.this, Pkey.password, ""));
        tvService.setOnClickListener(this);
        tvWechatPay.setOnClickListener(this);
        tvAliPay.setOnClickListener(this);

        if (SPUtils.getPrefInt(TopupActivity.this, Pkey.level, 0) == 0) {
            //未开通
            tvVipState.setText("未开通");
            tvVipNumber.setText("0");

        } else {
            //开通
            tvVipState.setText("已开通");
            tvVipNumber.setText(SPUtils.getPrefString(TopupActivity.this, Pkey.vip_date, ""));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvAliPay:
                dialog.setVisibility(View.VISIBLE);
                pay(pay_id_alipay);
                tvAliPay.setEnabled(false);
                tvWechatPay.setEnabled(false);
                break;
            case R.id.tvWechatPay:
                dialog.setVisibility(View.VISIBLE);
                pay(pay_id_wechat);
                tvAliPay.setEnabled(false);
                tvWechatPay.setEnabled(false);
                break;

        }
    }

    private void pay(int payWay) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("channel_id", payWay);
            map.put("set_meal_id", mVipEntity.getId());
            mq.okRequest().setParams(map).setFlag("pay").byGet(Urls.PAY, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void showConfirmDialog(String title, final String type) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(TopupActivity.this);
        View view = LayoutInflater.from(TopupActivity.this).inflate(R.layout.dialog_comfirm, null, false);
        final Button next = (Button) view.findViewById(R.id.next);
        final Button cancel = (Button) view.findViewById(R.id.cancel);
        final TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(title);
        if (type.equals("signIn")) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
        }
        final MaterialDialog dialog = builder
                .cancelable(false)
                .customView(view, false)
                .build();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_comfirm);
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = DensityUtil.dip2px(300);    //宽度设置为屏幕的0.55
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;    //高度设置为屏幕的0.28
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null && dialog.isShowing()) {
                    if (!type.equals("signIn")) {
                        //跳转客服
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("payment_info")) {

                if (NetResult.isSuccess3(TopupActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<vipEntity> list = JSON.parseArray(data.toJSONString(), vipEntity.class);
                    if (list != null && list.size() > 0) {
                        adapter = new TopupAdapter(TopupActivity.this, R.layout.item_top_up, list);
                        adapter.addHeaderView(mHeadView);
                        adapter.addFooterView(mFootView);
                        recycleView.setAdapter(adapter);
                        mVipEntity = list.get(0);
                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                                mVipEntity = (vipEntity) adapter.getItem(position);
                                adapter.configCheckMap(position);
                                adapter.notifyDataSetChanged();

                            }

                        });
                    }

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(TopupActivity.this, json.getString("message"));
                }
            } else if (flag.equals("payment_channel")) {
                if (NetResult.isSuccess3(TopupActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<payWay> list = JSON.parseArray(data.toJSONString(), payWay.class);
                    if (list != null && list.size() > 0) {
                        for (payWay pay : list) {
                            if (pay.getPay_type() == 1) {
                                //支付宝支付
                                tvAliPay.setVisibility(View.VISIBLE);
                                pay_id_alipay = pay.getId();
                            } else if (pay.getPay_type() == 2) {
                                //微信支付
                                tvWechatPay.setVisibility(View.VISIBLE);
                                pay_id_wechat = pay.getId();
                            }
                        }
                    }
                }
            } else if (flag.equals("pay")) {
                if (NetResult.isSuccess3(TopupActivity.this, success, object, error)) {
//                    if (ClickLimit.canClick()) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    PayResultEntity entity = JSON.parseObject(data.toJSONString(), PayResultEntity.class);
                    if (entity != null) {
                        Intent intent = new Intent(TopupActivity.this, MyWebView.class);
                        intent.putExtra("url", entity.getData().getPayUrl());
                        intent.putExtra("title", "支付");
                        intent.putExtra("type", "pay");
                        startActivity(intent);
                        dialog.setVisibility(View.GONE);
//                        finish();
                    }
                } else {
                    dialog.setVisibility(View.GONE);
                    tvAliPay.setEnabled(true);
                    tvWechatPay.setEnabled(true);
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    T.showMessage(TopupActivity.this, data.getString("msg"));
                }
            }
//            }
        } catch (Exception e) {

        }
    }
}
