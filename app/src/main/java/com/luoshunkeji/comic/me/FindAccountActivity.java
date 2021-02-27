package com.luoshunkeji.comic.me;

import android.os.Bundle;
import android.view.View;

import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.utils.ToastUtil;

import java.io.IOException;

public class FindAccountActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_account);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.tvConfirm).clicked(this);
        mq.id(R.id.title).text("账号找回");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvConfirm:
                if (mq.id(R.id.edtOrderNumber).getText().toString().equals("") ) {
                    ToastUtil.showToast("请输入商户账单");
                } else {

                }
                break;
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {

    }
}
