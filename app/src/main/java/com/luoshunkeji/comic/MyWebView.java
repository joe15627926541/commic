package com.luoshunkeji.comic;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.network.MQuery;

/**
 * Created by lsnw on 2020/3/28.
 */

public class MyWebView extends BaseFramActivity implements View.OnClickListener {

    private MQuery mq;
    private String type;//为pay为支付需要适配

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra("url");
        LinearLayout mWeblayout = (LinearLayout) findViewById(R.id.webview);
        if (url != null) {
            AgentWeb mAgentWeb = AgentWeb.with(this)//传入Activity
                    .setAgentWebParent(mWeblayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                    .useDefaultIndicator()// 使用默认进度条
                    .createAgentWeb()//
                    .ready()
                    .go(url);
            if (type.equals("pay")) {
                int screenDensity = getResources().getDisplayMetrics().densityDpi;
                WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                switch (screenDensity) {
                    case DisplayMetrics.DENSITY_LOW:
                        zoomDensity = WebSettings.ZoomDensity.CLOSE;
                        break;
                    case DisplayMetrics.DENSITY_MEDIUM:
                        zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                        break;
                    case DisplayMetrics.DENSITY_HIGH:
                    case DisplayMetrics.DENSITY_XHIGH:
                    case DisplayMetrics.DENSITY_XXHIGH:
                    default:
                        zoomDensity = WebSettings.ZoomDensity.FAR;
                        break;
                }
                mAgentWeb.getAgentWebSettings().getWebSettings().setDefaultZoom(zoomDensity);
//                webSettings.setDefaultZoom(zoomDensity);
            }
        }
    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        String title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        if (title != null) {
            mq.id(R.id.title).text(title);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
