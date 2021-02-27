package com.luoshunkeji.comic.read;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.ReadAdapter;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.T;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public class ReadActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {
    private MQuery mq;
    private RecyclerView recycleView;
    private View mFootView;
    private TextView tvFootDict, tvFootNext, tvFootPre, tvDict, tvNext, tvPre;
    private ReadAdapter adapter;
    int chapter_id;
    private Object previous_chapter_id;
    private Object next_chapter_id;
    private RelativeLayout layout, rlItem;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_read);
        chapter_id = getIntent().getIntExtra("chapter_id", 0);
    }

    @Override
    public void initData() {
        getChapter();
    }

    private void getChapter() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("chapter_id", chapter_id);
            mq.okRequest().setParams(map).setFlag("comic_show").byGet(Urls.COMIC_SHOW, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        layout = (RelativeLayout) findViewById(R.id.layout);
        rlItem = (RelativeLayout) findViewById(R.id.rlItem);
        mFootView = LayoutInflater.from(this)
                .inflate(R.layout.item_chapter_next, null);
        LinearLayoutManager VerticalLayoutManager = new LinearLayoutManager(this);
        VerticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(VerticalLayoutManager);
        //item_read    //底部的上下一章 item_chapter_next
        tvFootDict = (TextView) mFootView.findViewById(R.id.tvFootDict);
        tvFootNext = (TextView) mFootView.findViewById(R.id.tvFootNext);
        tvFootPre = (TextView) mFootView.findViewById(R.id.tvFootPre);
        tvDict = (TextView) findViewById(R.id.tvDict);
        tvNext = (TextView) findViewById(R.id.tvNext);
        tvPre = (TextView) findViewById(R.id.tvPre);
        tvFootDict.setOnClickListener(this);
        tvFootNext.setOnClickListener(this);
        tvFootPre.setOnClickListener(this);
        tvDict.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvPre.setOnClickListener(this);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //向上
                    layout.setVisibility(View.VISIBLE);
                } else {
                    //向下
                    layout.setVisibility(View.GONE);
                }
                rlItem.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tvDict:
            case R.id.tvFootDict:
                finish();
                break;

            case R.id.tvPre:
            case R.id.tvFootPre:

                NextOrPreChapter(previous_chapter_id, "pre");
                break;
            case R.id.tvNext:
            case R.id.tvFootNext:
                NextOrPreChapter(next_chapter_id, "next");
                break;

        }
    }

    public void NextOrPreChapter(Object next_pre_chapter_id, String flag) {
        try {
            if (next_pre_chapter_id == null) {
                if (flag.equals("pre")) {
                    T.showMessage(ReadActivity.this, "已经是第一章了");
                } else {
                    T.showMessage(ReadActivity.this, "已经是最后一章了");
                }
            } else {
                if ((int) next_pre_chapter_id > 0) {
                    chapter_id = (int) next_pre_chapter_id;
                    getChapter();
                    recycleView.scrollToPosition(0);
                } else {
                    if (flag.equals("pre")) {
                        T.showMessage(ReadActivity.this, "已经是第一章了");
                    } else {
                        T.showMessage(ReadActivity.this, "已经是最后一章了");
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("comic_show")) {
                if (NetResult.isSuccess3(ReadActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    JSONArray imageJSONArray = data.getJSONArray("images");
                    List<String> image = JSON.parseArray(imageJSONArray.toJSONString(), String.class);
                    if (adapter == null) {
                        adapter = new ReadAdapter(this, R.layout.item_read, image);
                        adapter.addFooterView(mFootView);
                        recycleView.setAdapter(adapter);
                        layout.setVisibility(View.VISIBLE);

                        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                switch (view.getId()) {
                                    case R.id.ivCover:
                                        if (layout.getVisibility() == View.VISIBLE) {
                                            layout.setVisibility(View.GONE);
                                            rlItem.setVisibility(View.GONE);
                                        } else {
                                            layout.setVisibility(View.VISIBLE);
                                            rlItem.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                }
                            }

                        });

                    } else {
                        adapter.setNewData(image);
                        adapter.notifyDataSetChanged();
                    }
                    mq.id(R.id.title).text(data.getString("name"));
                    next_chapter_id = data.getInteger("next_chapter_id");
                    previous_chapter_id = data.getInteger("previous_chapter_id");
                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(ReadActivity.this, json.getString("message"));
                    finish();
                }
            }
        } catch (Exception e) {

        }
    }
}
