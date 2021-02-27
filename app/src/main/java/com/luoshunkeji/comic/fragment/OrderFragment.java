package com.luoshunkeji.comic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.OrderAdapter;
import com.luoshunkeji.comic.dao.BaseFragment;
import com.luoshunkeji.comic.entity.OrderEntity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.T;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;


public class OrderFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, OkhttpUtils.OkhttpListener {
    private int mType;
    private View view;
    private RecyclerView recycleView;
    private OrderAdapter adapter;
    private int page = 1;
    private MQuery mq;
    LinearLayout emptyView;
    View mEmptyView;

    public OrderFragment(int type) {
        mType = type;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_no_more_data, null);
        return view;
    }

    @Override
    public void initData() {
        getOrderListByType();
    }

    private void getOrderListByType() {
        page = 1;
        try {
            HashMap<String, Object> map = new HashMap<>();
            if (mType > 0) {//“全部” 的不传
                map.put("sort", mType);
            }
            map.put("page", page);
            mq.okRequest().setParams(map).setFlag("order").byGet(Urls.ORDER, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void getAddOrderListByType(int page) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            if (mType > 0) {//“全部” 的不传
                map.put("sort", mType);
            }
            map.put("page", page);
            mq.okRequest().setParams(map).setFlag("add_order").byGet(Urls.ORDER, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        mq = new MQuery(getActivity());
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        emptyView = (LinearLayout) view.findViewById(R.id.emptyView);
        LinearLayoutManager VerticalLayoutManager = new LinearLayoutManager(getActivity());
        VerticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(VerticalLayoutManager);

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getAddOrderListByType(page);
    }


    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("order")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<OrderEntity> list = JSON.parseArray(data.toJSONString(), OrderEntity.class);
                        if (adapter == null) {
                            adapter = new OrderAdapter(getActivity(), R.layout.item_order, list);
                            adapter.setOnLoadMoreListener(this, recycleView);
                            adapter.setEmptyView(mEmptyView);
                            recycleView.setAdapter(adapter);
                        } else {
                            adapter.setNewData(list);
                            adapter.notifyDataSetChanged();
                        }

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(getActivity(), json.getString("message"));
                }

            } else if (flag.equals("add_order")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<OrderEntity> list = JSON.parseArray(data.toJSONString(), OrderEntity.class);
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


}
