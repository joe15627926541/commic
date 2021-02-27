package com.luoshunkeji.comic.read;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luoshunkeji.comic.Config;
import com.luoshunkeji.comic.MyApplication;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.ChapterAdapter;
import com.luoshunkeji.comic.adapter.RankAdapter;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.entity.Comics;
import com.luoshunkeji.comic.entity.DetailComicEntity;
import com.luoshunkeji.comic.me.ReadHistoryActivity;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.NetResult;
import com.luoshunkeji.comic.network.OkhttpUtils;
import com.luoshunkeji.comic.network.Urls;
import com.luoshunkeji.comic.utils.ClickLimit;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DetailComicActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {

    private MQuery mq;
    private RecyclerView recycleView, ChapterRecycleView;
    private RankAdapter adapter;
    private ChapterAdapter mChapterAdapter;
    private int id;
    private DetailComicEntity mDetailComicEntity;
    private ImageView ivCover;
    private boolean isClickFoot = false;
    private final Handler handler = new Handler();
    private RelativeLayout rlFoot;


    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_comic);
        id = getIntent().getIntExtra("id", 0);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initData() {
        getDetailComics();
    }

    private void getDetailComics() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            mq.okRequest().setParams(map).setFlag("comic_detail").byGet(Urls.COMIC_DETAIL, this);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initView() {
        mq = new MQuery(this);
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.tvReadHistory).clicked(this);
        mq.id(R.id.tvOrder).clicked(this);
        mq.id(R.id.tvShare).clicked(this);
        mq.id(R.id.tvHotMore).clicked(this);
        mq.id(R.id.right_img).clicked(this).image(R.mipmap.back_home);
        mq.id(R.id.rlFoot).clicked(this);
        recycleView = (RecyclerView) findViewById(R.id.HotRecycleView);
        rlFoot = (RelativeLayout) findViewById(R.id.rlFoot);
        ivCover = (ImageView) findViewById(R.id.ivCover);
        ChapterRecycleView = (RecyclerView) findViewById(R.id.ChapterRecycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ChapterRecycleView.setLayoutManager(linearLayoutManager);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recycleView.setLayoutManager(layoutManager);
        mq.id(R.id.tvHot).text("猜你喜欢");
        ChapterRecycleView.setNestedScrollingEnabled(false);
        recycleView.setNestedScrollingEnabled(false);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tvShare:

                break;

            case R.id.tvHotMore:
                EventBus.getDefault().post("select_recommend_fragment");
                MyApplication.closeAllActivity();
                finish();
                break;
            case R.id.right_img:
                EventBus.getDefault().post("select_home_fragment");
                MyApplication.closeAllActivity();
                finish();
                break;

            case R.id.tvOrder:
                //默认正序，展示降序
                if (ClickLimit.canClick1()) {
                    if (mDetailComicEntity.getChapter_list() != null && mDetailComicEntity.getChapter_list().size() > 0) {
                        if (mq.id(R.id.tvOrder).getText().equals("降序")) {
                            mq.id(R.id.tvOrder).text("升序");
                            Collections.reverse(mDetailComicEntity.getChapter_list());//倒序
                        } else {
                            mq.id(R.id.tvOrder).text("降序");

                            Collections.sort(mDetailComicEntity.getChapter_list(), COMPARATOR);
                        }

                        if (isClickFoot) {
                            //展示全部
                            mChapterAdapter.setNewData(mDetailComicEntity.getChapter_list());
                            mChapterAdapter.notifyDataSetChanged();
                        } else {
                            //展示五个
                            List<DetailComicEntity.ChapterListBean> chapter_list = mDetailComicEntity.getChapter_list();
                            mChapterAdapter.setNewData(chapter_list.subList(0, 5));
                            mChapterAdapter.notifyDataSetChanged();
                        }
                    }
                }

                break;
            case R.id.rlFoot:
                isClickFoot = true;
                rlFoot.setVisibility(View.GONE);
                mChapterAdapter.setNewData(mDetailComicEntity.getChapter_list());
                mChapterAdapter.loadMoreComplete();
                break;
            case R.id.tvReadHistory:
                startActivity(new Intent(DetailComicActivity.this, ReadHistoryActivity.class));
                break;

        }
    }

    private static final Comparator<DetailComicEntity.ChapterListBean> COMPARATOR = new Comparator<DetailComicEntity.ChapterListBean>() {
        @Override
        public int compare(DetailComicEntity.ChapterListBean lhs, DetailComicEntity.ChapterListBean rhs) {
            return lhs.getChapter_id() - rhs.getChapter_id();
        }
    };

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {
        try {
            if (flag.equals("comic_detail")) {
                if (NetResult.isSuccess3(DetailComicActivity.this, success, object, error)) {
                    JSONObject json = JSONObject.parseObject(object);
                    JSONObject data = json.getJSONObject("data");
                    mDetailComicEntity = JSON.parseObject(data.toJSONString(), DetailComicEntity.class);
                    if (mDetailComicEntity != null) {
                        mq.id(R.id.tvTitle).text(mDetailComicEntity.getComic_info().getName());
                        mq.id(R.id.tvState).text(mDetailComicEntity.getComic_info().getSerialise());
                        mq.id(R.id.tvChapterState).text(mDetailComicEntity.getComic_info().getSerialise());
                        mq.id(R.id.tvClick).text("点击量:" + mDetailComicEntity.getComic_info().getClick());
                        mq.id(R.id.tvSentiment).text("总人气:" + mDetailComicEntity.getComic_info().getPopularity());
                        mq.id(R.id.tvDec).text(mDetailComicEntity.getComic_info().getDescription());
                        mq.id(R.id.title).text(mDetailComicEntity.getComic_info().getName());

                        Glide.with(DetailComicActivity.this)
                                .load(SPUtils.getPrefString(DetailComicActivity.this, Pkey.cnd, Config.default_cnd) + mDetailComicEntity.getComic_info().getCover())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Glide.with(DetailComicActivity.this).asGif().load(R.drawable.loading).into(ivCover);
                                            }
                                        });
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .error(R.drawable.loading)
                                .into(ivCover);


                        if (mDetailComicEntity.getChapter_list() != null && mDetailComicEntity.getChapter_list().size() > 0) {
                            mq.id(R.id.tvChapter).text(mDetailComicEntity.getChapter_list().size() + "个章节");

                            List<DetailComicEntity.ChapterListBean> chapter_list = mDetailComicEntity.getChapter_list();
                            mChapterAdapter = new ChapterAdapter(this, R.layout.item_chapter, chapter_list.subList(0, 5));

                            ChapterRecycleView.setAdapter(mChapterAdapter);
                            mChapterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    DetailComicEntity.ChapterListBean bean = (DetailComicEntity.ChapterListBean) adapter.getItem(position);
                                    switch (view.getId()) {
                                        case R.id.rlItem:
                                            Intent intent = new Intent(DetailComicActivity.this, ReadActivity.class);
                                            intent.putExtra("chapter_id", bean.getChapter_id());
                                            startActivity(intent);
                                            break;
                                    }
                                }

                            });

                        }

                        if (mDetailComicEntity.getYou_like() != null && mDetailComicEntity.getYou_like().size() > 0) {
                            if (adapter == null) {
                                adapter = new RankAdapter(this, R.layout.item_hot, mDetailComicEntity.getYou_like());
                                recycleView.setAdapter(adapter);
                                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                                        Comics mComics = (Comics) adapter.getItem(position);
                                        switch (view.getId()) {
                                            case R.id.llItem:
                                                Intent intent = new Intent(DetailComicActivity.this, DetailComicActivity.class);
                                                intent.putExtra("id", mComics.getId());
                                                startActivity(intent);

                                                break;
                                        }
                                    }

                                });
                            }
                        }


                    }

                } else {
                    //token过期
                    JSONObject json = JSONObject.parseObject(object);
                    T.showMessage(DetailComicActivity.this, json.getString("message"));
                    finish();
                }
            }
        } catch (Exception e) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String taget) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
