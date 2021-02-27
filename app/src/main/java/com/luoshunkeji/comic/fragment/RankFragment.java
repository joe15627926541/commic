package com.luoshunkeji.comic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.RankAdapter;
import com.luoshunkeji.comic.dao.BaseFragment;
import com.luoshunkeji.comic.entity.Comics;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.read.DetailComicActivity;
import com.luoshunkeji.comic.utils.ClickLimit;
import com.luoshunkeji.comic.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RankFragment extends BaseFragment implements OkhttpUtils.OkhttpListener, BaseQuickAdapter.RequestLoadMoreListener {
    private MQuery mq;
    private String mType;
    private int page = 1;
    private View view;
    private RecyclerView recycleView;
    private RankAdapter adapter;
    private boolean isVisible;

    public RankFragment(String type) {
        mType = type;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void initData() {
        getRankListByType();
    }

    private void getRankListByType() {
        page = 1;
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("sort", mType);
            map.put("page", page);
            mq.okRequest().setParams(map).setFlag("get_comics").byGet(Urls.GET_COMICS, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private void searchComics(String searchContent) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("wd", searchContent);
            mq.okRequest().setParams(map).setFlag("search_comics").byGet(Urls.SEARCH_COMICS, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


    private void getAddRankListByType(int page) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("sort", mType);
            map.put("page", page);
            mq.okRequest().setParams(map).setFlag("get_add_ranklist_bytype").byGet(Urls.GET_COMICS, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        mq = new MQuery(getActivity());
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recycleView.setLayoutManager(layoutManager);

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getAddRankListByType(page);

    }


    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("get_comics")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<Comics> list = JSON.parseArray(data.toJSONString(), Comics.class);
                    if (list != null && list.size() > 0) {
                        if (adapter == null) {
                            adapter = new RankAdapter(getActivity(), R.layout.item_hot, list);
                            adapter.setOnLoadMoreListener(this, recycleView);
                            recycleView.setAdapter(adapter);
                            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                                    Comics mComics = (Comics) adapter.getItem(position);
                                    switch (view.getId()) {
                                        case R.id.llItem:
                                            if (ClickLimit.canClick1()) {
                                                Intent intent = new Intent(getActivity(), DetailComicActivity.class);
                                                intent.putExtra("id", mComics.getId());
                                                startActivity(intent);
                                            }
                                            break;
                                    }
                                }

                            });
                        } else {
                            adapter.setNewData(list);
                            adapter.notifyDataSetChanged();
                        }
                    }

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(getActivity(), json.getString("message"));
                }
            } else if (flag.equals("search_comics")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<Comics> list = JSON.parseArray(data.toJSONString(), Comics.class);
                    if (list != null && list.size() >= 0) {
                        if (adapter != null && isVisible) {
                            adapter.setNewData(list);
                            adapter.notifyDataSetChanged();
                        }

                        if (list != null && list.size() == 0) {
                            T.showMessage(getActivity(), "没找到相关数据!");
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideInput();
                            }
                        });
                    } else {
                        T.showMessage(getActivity(), "没找到相关数据!");
                    }
                }
            } else if (flag.equals("get_add_ranklist_bytype")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<Comics> list = JSON.parseArray(data.toJSONString(), Comics.class);
                    if (adapter != null) {
                        if (list.size() > 0) {
                            adapter.addData(list);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreComplete();
                            adapter.setEnableLoadMore(false);
                            adapter.setFooterView(LayoutInflater.from(getContext()).inflate(R.layout.view_no_more_data, null));
                        }
                    }
                }

            }
        } catch (Exception e) {

        }
    }

    protected void hideInput() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        View v = getActivity().getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String searchContent) {
        if (isVisible && !searchContent.contains("select_recommend_fragment") && !searchContent.contains("select_home_fragment")) {
            //select_recommend_fragment   这个字段代表漫画的详情页的猜你喜欢的“更多”,或者首页Tab 中的“更多”切换至推荐tab
            //select_home_fragment 这个字段代表漫画的详情页的“主页返回键”一键返回首页
            //这两个字段发过来的通知不搜索，搜索只在搜索框文字变化时处理
            searchComics(searchContent);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }
}
