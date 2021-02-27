package com.luoshunkeji.comic.me;

import android.content.Intent;
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
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.utils.DensityUtil;

import java.io.IOException;

public class ComplanitActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {
    private MQuery mq;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_complanit);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.tvComplanit).clicked(this);
        mq.id(R.id.tvService).clicked(this);
        mq.id(R.id.title).text("客服投诉");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvComplanit:
                startActivity(new Intent(ComplanitActivity.this, FeedBackActivity.class));
                break;
            case R.id.tvService:
                showConfirmDialog("是否跳转客服页面", "jump2service");
                break;

        }
    }
    private void showConfirmDialog(String title, final String type) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_comfirm, null, false);
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

    }
}
