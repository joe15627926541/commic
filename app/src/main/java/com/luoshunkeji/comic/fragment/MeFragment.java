package com.luoshunkeji.comic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.dao.BaseFragment;
import com.luoshunkeji.comic.me.BindPhoneActivity;
import com.luoshunkeji.comic.me.ChangeAccountActivity;
import com.luoshunkeji.comic.me.FeedBackActivity;
import com.luoshunkeji.comic.me.FindAccountActivity;
import com.luoshunkeji.comic.me.OrderActivity;
import com.luoshunkeji.comic.me.PasswordUpdateActivity;
import com.luoshunkeji.comic.me.ReadHistoryActivity;
import com.luoshunkeji.comic.me.TopupActivity;
import com.luoshunkeji.comic.me.WelfareCneterActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.DensityUtil;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.TimeUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class MeFragment extends BaseFragment implements OkhttpUtils.OkhttpListener, View.OnClickListener {
    private View view;
    private MQuery mq;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mq.id(R.id.tvName).text("账号: " + SPUtils.getPrefString(getActivity(), Pkey.username, ""));
        mq.id(R.id.tvPasswd).text("密码: " + SPUtils.getPrefString(getActivity(), Pkey.password, ""));
        mq.id(R.id.tvCoinNumber).text(SPUtils.getPrefInt(getActivity(), Pkey.currency, 0) + "");
        if (SPUtils.getPrefInt(getActivity(), Pkey.level, 0) == 0) {
            //未开通
            mq.id(R.id.tvVipState).text("未开通");
            mq.id(R.id.tvVipTime).text("0");

        } else {
            //开通
            mq.id(R.id.tvVipState).text("已开通");
            mq.id(R.id.tvVipTime).text(SPUtils.getPrefString(getActivity(), Pkey.vip_date, ""));
        }
        if (SPUtils.getPrefString(getActivity(), Pkey.be_present_date, "").equals(TimeUtils.getCurTimeLong("yyyy-MM-dd"))) {
            //已经签到
            mq.id(R.id.tvSign).text("已签到");
            mq.id(R.id.tvSignTip).text("");
        } else {
            //未签到
            mq.id(R.id.tvSign).text("每日签到");
            mq.id(R.id.tvSignTip).text("(每日领100金币)");
        }
        mq.id(R.id.tvMoney).text("(余额:"+SPUtils.getPrefInt(getActivity(), Pkey.currency, 0)+")");

    }

    @Override
    public void initView() {
        mq = new MQuery(view);
        mq.id(R.id.rlTopup).clicked(this);
        mq.id(R.id.rlSignIn).clicked(this);
        mq.id(R.id.rlWelfare).clicked(this);
        mq.id(R.id.rlBind).clicked(this);
        mq.id(R.id.rlService).clicked(this);
        mq.id(R.id.rlEdit).clicked(this);
        mq.id(R.id.rlFeedBack).clicked(this);
        mq.id(R.id.rlReadHistory).clicked(this);
        mq.id(R.id.rlOrder).clicked(this);
        mq.id(R.id.rlFindAccount).clicked(this);
        mq.id(R.id.tvChangeId).clicked(this);
    }

    private void showConfirmDialog(String title, final String type) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_comfirm, null, false);
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
                    } else {
                        //签到
                        signIn();
                    }
                    dialog.dismiss();
                }
            }
        });
    }

    private void signIn() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("be_present").byPost(Urls.BE_PRESENT, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlTopup:
                //充值
                startActivity(new Intent(getActivity(), TopupActivity.class));
                break;
            case R.id.rlSignIn:
                //签到
                String title = "已签到";
                if (mq.id(R.id.tvSign).getText().equals("每日签到")) {
                    title = "签到成功";
                }
                showConfirmDialog(title, "signIn");
                break;
            case R.id.rlService:
                //客服
                showConfirmDialog("是否跳转客服页面", "jump2service");
                break;
            case R.id.rlWelfare:
                //福利中心
                startActivity(new Intent(getActivity(), WelfareCneterActivity.class));
                break;
            case R.id.rlBind:
                //绑定账户
                startActivity(new Intent(getActivity(), BindPhoneActivity.class));
                break;
            case R.id.rlEdit:
                //修改账户密码
                startActivity(new Intent(getActivity(), PasswordUpdateActivity.class));
                break;
            case R.id.rlFeedBack:
                //反馈
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.rlReadHistory:
                //阅读历史
                startActivity(new Intent(getActivity(), ReadHistoryActivity.class));
                break;
            case R.id.rlOrder:
                //我的订单
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.rlFindAccount:
                //找回账户
                startActivity(new Intent(getActivity(), FindAccountActivity.class));
                break;
            case R.id.tvChangeId:
                //切换账号
                startActivity(new Intent(getActivity(), ChangeAccountActivity.class));
                break;
        }

    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {

        try {
            if (flag.equals("be_present")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    SPUtils.setPrefString(getActivity(), Pkey.be_present_date, TimeUtils.getCurTimeLong("yyyy-MM-dd"));
                    mq.id(R.id.tvSign).text("已签到");
                    mq.id(R.id.tvSignTip).text("");

                }
            }
        } catch (Exception e) {

        }
    }
}
