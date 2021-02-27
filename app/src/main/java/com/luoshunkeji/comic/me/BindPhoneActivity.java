package com.luoshunkeji.comic.me;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.T;
import com.luoshunkeji.comic.utils.ToastUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class BindPhoneActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_phone);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.title).text("修改用户和密码");
        mq.id(R.id.tvBind).clicked(this);
        mq.id(R.id.tvTip).text("修改后可以根据新账号进行登录\n请记住您的用户名" + SPUtils.getPrefString(this, Pkey.username, ""));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvBind:

                if (mq.id(R.id.edtPhone).getText().toString().equals("")) {
                    ToastUtil.showToast("请输入手机号码");
                } else {
                    //绑定账号
                    BindPhone();
                }
                break;
        }
    }

    private void BindPhone() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            if (!mq.id(R.id.edtPhone).getText().toString().equals("")) {
                map.put("phone", mq.id(R.id.edtPhone).getText().toString());
            }
            if (!mq.id(R.id.edtPassword).getText().toString().equals("")) {
                map.put("password", mq.id(R.id.edtPassword).getText().toString());
            }
            mq.okRequest().setParams(map).setFlag("bind_phone").byPost(Urls.BIND_PHONE, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("bind_phone")) {
                if (NetResult.isSuccess3(BindPhoneActivity.this, success, object, error)) {
                    T.showMessage(BindPhoneActivity.this, "绑定成功");
                    if (mq.id(R.id.edtPassword).getText().toString().equals("")) {
                        SPUtils.setPrefString(this, Pkey.password, "123456");
                    } else {
                        SPUtils.setPrefString(this, Pkey.password, mq.id(R.id.edtPassword).getText().toString());
                    }

                    SPUtils.setPrefString(this, Pkey.username, mq.id(R.id.edtPhone).getText().toString());
                    finish();

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(BindPhoneActivity.this, json.getString("message"));
                }
            }
        } catch (Exception e) {

        }
    }
}
