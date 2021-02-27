package com.luoshunkeji.comic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONObject;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.T;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SplashActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener {
    // 延迟1秒
    private static final long SPLASH_DELAY_MILLIS = 1000;
    private static final int GO_HOME = 1001;
    private MQuery mq;
    private String token;

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //不是第一次开启
            switch (msg.what) {
                case GO_HOME:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void createActivity(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalsh);
    }

    @Override
    public void initData() {
        mq = new MQuery(this);
        token = SPUtils.getPrefString(this, Pkey.token, "");
        if (token != null && !token.equals("")) {
            getUserInfo();
        } else {
            //请求
            getToken();
        }
    }

    private void getToken() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("get_token").byGet(Urls.GET_TOKEN, SplashActivity.this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private void getAppInfo() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("get_appinfo").byGet(Urls.GET_APPINFO, SplashActivity.this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("get_userinfo").byGet(Urls.GET_USERINFO, SplashActivity.this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("get_token")) {
                if (NetResult.isSuccess3(this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    SPUtils.setPrefString(SplashActivity.this, Pkey.token, data.getString("auth_token"));
                    SPUtils.setPrefInt(SplashActivity.this, Pkey.user_id, data.getInteger("user_id"));
                    SPUtils.setPrefString(SplashActivity.this, Pkey.username, data.getString("username"));
                    getUserInfo();
                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(SplashActivity.this, json.getString("message"));
                    finish();
                }
            } else if (flag.equals("get_appinfo")) {
                if (NetResult.isSuccess3(this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    SPUtils.setPrefString(SplashActivity.this, Pkey.cnd, data.getString("cnd"));
                    SPUtils.setPrefString(SplashActivity.this, Pkey.system_name, data.getString("system_name"));
                    SPUtils.setPrefString(SplashActivity.this, Pkey.logo, data.getString("logo"));
                    mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(SplashActivity.this, json.getString("message"));
                    finish();
                }
            } else if (flag.equals("get_userinfo")) {
                if (NetResult.isSuccess3(this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    SPUtils.setPrefInt(SplashActivity.this, Pkey.level, data.getInteger("level"));
                    SPUtils.setPrefInt(SplashActivity.this, Pkey.currency, data.getInteger("currency"));
                    SPUtils.setPrefString(SplashActivity.this, Pkey.password, data.getString("password"));
                    SPUtils.setPrefString(SplashActivity.this, Pkey.be_present_date, data.getString("be_present_date"));
                    if (data.getInteger("level") == 1) {
                        SPUtils.setPrefString(SplashActivity.this, Pkey.vip_date, data.getString("vip_date"));
                    }
                    getAppInfo();
                }
            }
        } catch (Exception e) {

        }
    }


}
