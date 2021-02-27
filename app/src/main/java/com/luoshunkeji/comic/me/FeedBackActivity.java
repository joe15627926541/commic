package com.luoshunkeji.comic.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.T;
import com.luoshunkeji.comic.utils.ToastUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class FeedBackActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;
    private EditText edtContent;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.tvSend).clicked(this);
        mq.id(R.id.title).text("意见反馈");
        edtContent = (EditText) findViewById(R.id.edtContent);
        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = edtContent.getText().toString().length();
                mq.id(R.id.tvLength).text("您还可以输入" + (180 - length) + "字");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvSend:
                if (mq.id(R.id.edtPhone).getText().toString().equals("")) {
                    ToastUtil.showToast("联系方式不能为空");
                } else if (mq.id(R.id.edtContent).getText().toString().equals("")) {
                    ToastUtil.showToast("反馈内容不能为空");
                } else {
                    //发送
                    sendFeedBack();
                }
                break;
        }
    }

    private void sendFeedBack() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", mq.id(R.id.edtPhone).getText().toString());
            map.put("content", mq.id(R.id.edtContent).getText().toString());
            mq.okRequest().setParams(map).setFlag("feedback").byPost(Urls.FEEDBACK, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("feedback")) {
                if (NetResult.isSuccess3(FeedBackActivity.this, success, object, error)) {
                    T.showMessage(FeedBackActivity.this,"反馈成功");
                    finish();
                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(FeedBackActivity.this, json.getString("message"));
                }
            }
        } catch (Exception e) {

        }
    }
}
