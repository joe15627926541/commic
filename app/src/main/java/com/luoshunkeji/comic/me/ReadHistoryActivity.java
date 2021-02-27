package com.luoshunkeji.comic.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.ReadHistoryAdapter;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.entity.ReadHistoryEntity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.read.DetailComicActivity;
import com.luoshunkeji.comic.utils.ClickLimit;
import com.luoshunkeji.comic.utils.T;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public class ReadHistoryActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView recycleView;
    private ReadHistoryAdapter adapter;
    private MQuery mq;
    private int page = 1;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_read_history);
    }

    @Override
    public void initData() {
        getHistory();
    }

    private void getHistory() {
        page = 1;
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("page", page);
            mq.okRequest().setParams(map).setFlag("read_history").byGet(Urls.READ_HISTORY, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void getAddHistory(int page) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("page", page);
            mq.okRequest().setParams(map).setFlag("add_read_history").byGet(Urls.READ_HISTORY, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.title).text("阅读记录");
        mq.id(R.id.back).clicked(this);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("read_history")) {
                if (NetResult.isSuccess3(ReadHistoryActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<ReadHistoryEntity> list = JSON.parseArray(data.toJSONString(), ReadHistoryEntity.class);

                    adapter = new ReadHistoryAdapter(ReadHistoryActivity.this, R.layout.item_read_history, list);
                    adapter.setOnLoadMoreListener(this, recycleView);
                    adapter.setEmptyView(LayoutInflater.from(ReadHistoryActivity.this).inflate(R.layout.view_no_more_data, null));
                    recycleView.setAdapter(adapter);
                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            ReadHistoryEntity item = (ReadHistoryEntity) adapter.getItem(position);
                            switch (view.getId()) {
                                case R.id.llItem:
                                    if (ClickLimit.canClick1()) {
                                        Intent intent = new Intent(ReadHistoryActivity.this, DetailComicActivity.class);
                                        intent.putExtra("id", item.getComic_id());
                                        startActivity(intent);
                                    }
                                    break;
                            }
                        }
                    });

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(ReadHistoryActivity.this, json.getString("message"));
                }
            } else if (flag.equals("add_read_history")) {
                if (NetResult.isSuccess3(ReadHistoryActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<ReadHistoryEntity> list = JSON.parseArray(data.toJSONString(), ReadHistoryEntity.class);
                    if (adapter != null) {
                        if (list.size() > 0) {
                            adapter.addData(list);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreComplete();
                            adapter.setEnableLoadMore(false);
                            adapter.setFooterView(LayoutInflater.from(ReadHistoryActivity.this).inflate(R.layout.view_no_more_data, null));
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onLoadMoreRequested() {
        //加载更多
        page++;
        getAddHistory(page);

    }


}
