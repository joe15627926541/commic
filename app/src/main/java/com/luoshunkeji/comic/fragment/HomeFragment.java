package com.luoshunkeji.comic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.luoshunkeji.comic.Config;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.HotAdapter;
import com.luoshunkeji.comic.adapter.TopAdapter;
import com.luoshunkeji.comic.adapter.YouLikeAdapter;
import com.luoshunkeji.comic.dao.BaseFragment;
import com.luoshunkeji.comic.entity.HomeEntity;
import com.luoshunkeji.comic.entity.LocalImageHolderView;
import com.luoshunkeji.comic.me.ComplanitActivity;
import com.luoshunkeji.comic.me.TopupActivity;
import com.luoshunkeji.comic.me.WelfareCneterActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.read.DetailComicActivity;
import com.luoshunkeji.comic.utils.ClickLimit;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends BaseFragment implements OkhttpUtils.OkhttpListener, View.OnClickListener {
    private View view;
    private MQuery mq;
    private ConvenientBanner banner;
    private ArrayList<String> img_list;

    private LinearLayout llTopRank, llWelfareCenter, llVip, llComplaint;
    private TextView tvTopMore, tvHotMore, tvUpdateMore, tvEndMore, tvMustSeeMore, tvYouLikeMore;
    private RecyclerView TopRecycleView, HotRecycleView, UpdateRecycleView, EndRecycleView, MustSeeRecycleView, YouLikeRecycleView;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void initData() {
        getHome();
    }


    private void getHome() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            mq.okRequest().setParams(map).setFlag("get_home").byGet(Urls.GET_HOME, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        mq = new MQuery(getActivity());
        banner = (ConvenientBanner) view.findViewById(R.id.banner);
        llTopRank = (LinearLayout) view.findViewById(R.id.llTopRank);
        llWelfareCenter = (LinearLayout) view.findViewById(R.id.llWelfareCenter);
        llVip = (LinearLayout) view.findViewById(R.id.llVip);
        llComplaint = (LinearLayout) view.findViewById(R.id.llComplaint);
        llTopRank.setOnClickListener(this);
        llWelfareCenter.setOnClickListener(this);
        llVip.setOnClickListener(this);
        llComplaint.setOnClickListener(this);

        tvHotMore = (TextView) view.findViewById(R.id.tvHotMore);
        tvTopMore = (TextView) view.findViewById(R.id.tvTopMore);
        tvUpdateMore = (TextView) view.findViewById(R.id.tvUpdateMore);
        tvEndMore = (TextView) view.findViewById(R.id.tvEndMore);
        tvMustSeeMore = (TextView) view.findViewById(R.id.tvMustSeeMore);
        tvYouLikeMore = (TextView) view.findViewById(R.id.tvYouLikeMore);

        HotRecycleView = (RecyclerView) view.findViewById(R.id.HotRecycleView);
        TopRecycleView = (RecyclerView) view.findViewById(R.id.TopRecycleView);
        UpdateRecycleView = (RecyclerView) view.findViewById(R.id.UpdateRecycleView);
        EndRecycleView = (RecyclerView) view.findViewById(R.id.EndRecycleView);
        MustSeeRecycleView = (RecyclerView) view.findViewById(R.id.MustSeeRecycleView);
        YouLikeRecycleView = (RecyclerView) view.findViewById(R.id.YouLikeRecycleView);


        //item_tab 头部分类   item_home //整个书籍布局   item_hot  //热门分类书籍    item_top //人气Top    item_like//猜你喜欢
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        HotRecycleView.setLayoutManager(layoutManager);
        HotRecycleView.setNestedScrollingEnabled(false);


        LinearLayoutManager VerticalLayoutManager2 = new LinearLayoutManager(getActivity());
        VerticalLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        TopRecycleView.setLayoutManager(VerticalLayoutManager2);
        TopRecycleView.setNestedScrollingEnabled(false);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        UpdateRecycleView.setLayoutManager(layoutManager2);
        UpdateRecycleView.setNestedScrollingEnabled(false);


        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 3);
        EndRecycleView.setLayoutManager(layoutManager3);
        EndRecycleView.setNestedScrollingEnabled(false);


        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(), 3);
        MustSeeRecycleView.setLayoutManager(layoutManager4);
        MustSeeRecycleView.setNestedScrollingEnabled(false);


        LinearLayoutManager VerticalLayoutManager = new LinearLayoutManager(getActivity());
        VerticalLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        YouLikeRecycleView.setLayoutManager(VerticalLayoutManager);
        YouLikeRecycleView.setNestedScrollingEnabled(false);


        tvHotMore.setOnClickListener(this);
        tvTopMore.setOnClickListener(this);
        tvUpdateMore.setOnClickListener(this);
        tvEndMore.setOnClickListener(this);
        tvMustSeeMore.setOnClickListener(this);
        tvYouLikeMore.setOnClickListener(this);
        banner.startTurning(3000);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llTopRank:
            case R.id.tvHotMore:
            case R.id.tvTopMore:
            case R.id.tvEndMore:
            case R.id.tvUpdateMore:
            case R.id.tvMustSeeMore:
            case R.id.tvYouLikeMore:
                EventBus.getDefault().post("select_recommend_fragment");
                break;

            case R.id.llWelfareCenter:
                startActivity(new Intent(getActivity(), WelfareCneterActivity.class));

                break;
            case R.id.llVip:
                startActivity(new Intent(getActivity(), TopupActivity.class));
                break;
            case R.id.llComplaint:
                startActivity(new Intent(getActivity(), ComplanitActivity.class));
                break;
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("get_home")) {
                if (NetResult.isSuccess3(getActivity(), success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONArray data = json.getJSONArray("data");
                    List<HomeEntity> list = JSON.parseArray(data.toJSONString(), HomeEntity.class);
                    if (list != null && list.size() > 0) {
                        for (final HomeEntity bannerEntity : list) {
                            if (bannerEntity.getTitle().contains("Banner轮播大图")) {
                                //遍历出轮播图集合
                                img_list = new ArrayList<>();
                                for (int i = 0; i < bannerEntity.getData().size(); i++) {
                                    img_list.add(SPUtils.getPrefString(getActivity(), Pkey.cnd, Config.default_cnd) + bannerEntity.getData().get(i).getCover_h());
                                }
                                banner.setPages(
                                        new CBViewHolderCreator<LocalImageHolderView>() {
                                            @Override
                                            public LocalImageHolderView createHolder() {
                                                return new LocalImageHolderView();
                                            }
                                        }, img_list)
                                        .setPageIndicator(new int[]{R.mipmap.goos_detail_spot_off, R.mipmap.goos_detail_spot_on})
                                        .setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position) {
                                                if (ClickLimit.canClick1()) {
                                                    Intent intent = new Intent(getActivity(), DetailComicActivity.class);
                                                    intent.putExtra("id", bannerEntity.getData().get(position).getComic_id());
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            } else if (bannerEntity.getTitle().contains("热门推荐")) {
                                HotAdapter adapter = new HotAdapter(getActivity(), R.layout.item_hot, bannerEntity.getData());
                                HotRecycleView.setAdapter(adapter);
                            } else if (bannerEntity.getTitle().contains("最新更新")) {
                                HotAdapter updateAdapter = new HotAdapter(getActivity(), R.layout.item_hot, bannerEntity.getData());
                                UpdateRecycleView.setAdapter(updateAdapter);
                            } else if (bannerEntity.getTitle().contains("完结精品")) {
                                HotAdapter EndAdapter = new HotAdapter(getActivity(), R.layout.item_hot, bannerEntity.getData());
                                EndRecycleView.setAdapter(EndAdapter);
                            } else if (bannerEntity.getTitle().contains("经典必看")) {
                                HotAdapter MustSeeAdapter = new HotAdapter(getActivity(), R.layout.item_hot, bannerEntity.getData());
                                MustSeeRecycleView.setAdapter(MustSeeAdapter);
                            } else if (bannerEntity.getTitle().contains("人气TOP")) {
                                TopAdapter TopAdapter = new TopAdapter(getActivity(), R.layout.item_top, bannerEntity.getData());
                                TopRecycleView.setAdapter(TopAdapter);
                            } else if (bannerEntity.getTitle().contains("猜你喜欢")) {
                                YouLikeAdapter YouLikeAdapter = new YouLikeAdapter(getActivity(), R.layout.item_like, bannerEntity.getData());
                                YouLikeRecycleView.setAdapter(YouLikeAdapter);
                            }
                        }
                    }

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(getActivity(), json.getString("message"));
                }
            }
        } catch (Exception e) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String tager) {

    }

}
