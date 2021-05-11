package com.luoshunkeji;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.utils.ImageUtils;

public class TestActivity extends BaseFramActivity {
    private ImageView iv;
    private final Handler handler = new Handler();

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initData() {
       //this is a line test code
        Log.d("", "sdjfhksdhfkshf");
    }

    @Override
    public void initView() {
        iv = (ImageView) findViewById(R.id.iv);
        ImageUtils.loadOriginalImageView(this, "http://jp.jtgckj.net/images/collection/comic/297/1908882.jpg", iv, R.drawable.loading, R.drawable.loading);


    }
}
