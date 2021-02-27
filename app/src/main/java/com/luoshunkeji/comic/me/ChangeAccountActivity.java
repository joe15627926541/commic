package com.luoshunkeji.comic.me;

import android.content.Intent;
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

public class ChangeAccountActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {
    private MQuery mq;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_account);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.title).text("切换账户");
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.tvLogin).clicked(this);
        mq.id(R.id.tvFindAccount).clicked(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvFindAccount:
                startActivity(new Intent(ChangeAccountActivity.this, FindAccountActivity.class));
                break;
            case R.id.tvLogin:

                if (mq.id(R.id.edtPhone).getText().toString().equals("")) {
                    ToastUtil.showToast("请输入账户名或手机号");
                } else if (mq.id(R.id.edtPassword).getText().toString().equals("")) {
                    ToastUtil.showToast("请输入登录密码");
                } else {
                    //切换账号
                    changeAccount();
                }

                break;
        }
    }

    private void changeAccount() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("username", mq.id(R.id.edtPhone).getText().toString());
            map.put("password", mq.id(R.id.edtPassword).getText().toString());
            mq.okRequest().setParams(map).setFlag("sign_in").byPost(Urls.SIGN_IN, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void getAppInfo() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("get_appinfo").byGet(Urls.GET_APPINFO, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("get_userinfo").byGet(Urls.GET_USERINFO, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("sign_in")) {
                if (NetResult.isSuccess3(ChangeAccountActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.token, data.getString("auth_token"));
                    SPUtils.setPrefInt(ChangeAccountActivity.this, Pkey.user_id, data.getInteger("user_id"));
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.username, data.getString("username"));
                    getUserInfo();

                } else {
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(ChangeAccountActivity.this, json.getString("message"));
                }
            } else if (flag.equals("get_appinfo")) {
                if (NetResult.isSuccess3(this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.cnd, data.getString("cnd"));
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.system_name, data.getString("system_name"));
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.logo, data.getString("logo"));
                }
                finish();
            } else if (flag.equals("get_userinfo")) {
                if (NetResult.isSuccess3(this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    SPUtils.setPrefInt(ChangeAccountActivity.this, Pkey.level, data.getInteger("level"));
                    SPUtils.setPrefInt(ChangeAccountActivity.this, Pkey.currency, data.getInteger("currency"));
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.password, data.getString("password"));
                    SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.be_present_date, data.getString("be_present_date"));
                    if (data.getInteger("level") == 1) {
                        SPUtils.setPrefString(ChangeAccountActivity.this, Pkey.vip_date, data.getString("vip_date"));
                    }
                    getAppInfo();
                }
            }
        } catch (Exception e) {

        }
    }
}
