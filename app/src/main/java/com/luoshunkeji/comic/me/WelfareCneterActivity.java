package com.luoshunkeji.comic.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.dao.BaseFramActivity;
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

public class WelfareCneterActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welfare_center);
    }

    @Override
    public void initData() {
        if (SPUtils.getPrefString(this, Pkey.be_present_date, "").equals(TimeUtils.getCurTimeLong("yyyy-MM-dd"))) {
            //已经签到
            mq.id(R.id.tvSign).text("已签到");
        } else {
            //未签到
            mq.id(R.id.tvSign).text("未签到");

        }
    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.rlSignIn).clicked(this);
        mq.id(R.id.rlInvite).clicked(this);
        mq.id(R.id.rlShare).clicked(this);
        mq.id(R.id.rlGetVip).clicked(this);
        mq.id(R.id.title).text("福利中心");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rlSignIn:
                //签到
                String title = "签到成功";
                if (!mq.id(R.id.tvSign).getText().equals("未签到")) {
                    title = "已签到";
                }
                showConfirmDialog(title);
                break;
            case R.id.rlInvite:

                break;
            case R.id.rlShare:
                showConfirmDialog("快去漫画页面分享吧!");
                break;
            case R.id.rlGetVip:
                showConfirmDialog("没有完成任务");
                break;
        }
    }

    private void showConfirmDialog(final String title) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_comfirm, null, false);
        final Button next = (Button) view.findViewById(R.id.next);
        final TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(title);
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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (title.contains("签到")){
                    signIn();
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
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {

        try {
            if (flag.equals("be_present")) {
                if (NetResult.isSuccess3(WelfareCneterActivity.this, success, object, error)) {
                    SPUtils.setPrefString(WelfareCneterActivity.this, Pkey.be_present_date, TimeUtils.getCurTimeLong("yyyy-MM-dd"));
                    mq.id(R.id.tvSign).text("已签到");

                }
            }
        } catch (Exception e) {

        }
    }
}
