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

public class PasswordUpdateActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_password_update);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.title).text("修改登录账号名");
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.tvConfirm).clicked(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvConfirm:

                if (mq.id(R.id.edtPhone).getText().toString().equals("")) {
                    ToastUtil.showToast("请把内容填写完整!");
                } else if (mq.id(R.id.edtPassword).getText().toString().equals("")) {
                    ToastUtil.showToast("请把内容填写完整!");
                } else if (mq.id(R.id.edtNewPassword).getText().toString().equals("")) {
                    ToastUtil.showToast("请把内容填写完整!");
                } else if (mq.id(R.id.edtConfirmPassword).getText().toString().equals("")) {
                    ToastUtil.showToast("请把内容填写完整!");
                } else if (!mq.id(R.id.edtPassword).getText().toString().equals(SPUtils.getPrefString(PasswordUpdateActivity.this, Pkey.password, ""))) {
                    ToastUtil.showToast("密码不一致");
                } else if (!mq.id(R.id.edtConfirmPassword).getText().toString().equals(mq.id(R.id.edtNewPassword).getText().toString())) {
                    ToastUtil.showToast("密码不一致");
                } else {
                    BindPhone();
                }


                break;
        }
    }

    private void BindPhone() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("phone", mq.id(R.id.edtPhone).getText().toString());
            map.put("password", mq.id(R.id.edtNewPassword).getText().toString());
            mq.okRequest().setParams(map).setFlag("bind_phone").byPost(Urls.BIND_PHONE, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("bind_phone")) {
                if (NetResult.isSuccess3(PasswordUpdateActivity.this, success, object, error)) {
                    T.showMessage(PasswordUpdateActivity.this, "修改成功");
                    SPUtils.setPrefString(this, Pkey.password, mq.id(R.id.edtNewPassword).getText().toString());
                    SPUtils.setPrefString(this, Pkey.username, mq.id(R.id.edtPhone).getText().toString());
                    finish();

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(PasswordUpdateActivity.this, json.getString("message"));
                }
            }
        } catch (Exception e) {

        }
    }
}
