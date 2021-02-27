package com.luoshunkeji.comic.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luoshunkeji.comic.Config;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.entity.HomeEntity;
import com.luoshunkeji.comic.read.DetailComicActivity;
import com.luoshunkeji.comic.utils.ClickLimit;
import com.luoshunkeji.comic.utils.DensityUtil;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.ScreenUtil;

import java.util.List;

public class TopAdapter extends BaseQuickAdapter<HomeEntity.DataBean, BaseViewHolder> {
    private Activity mActivity;
    private List<HomeEntity.DataBean> list;
    private final Handler handler = new Handler();

    public TopAdapter(Activity activity, int layoutResId, @Nullable List<HomeEntity.DataBean> data) {
        super(layoutResId, data);
        list = data;
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeEntity.DataBean item) {
        LinearLayout llItem = helper.getView(R.id.llItem);
        final ImageView ivCover = helper.getView(R.id.ivCover);
        final TextView tvRank = helper.getView(R.id.tvRank);
        helper.setText(R.id.tvTitle, item.getName());
        helper.setText(R.id.tvClick, item.getClick()+"");
        helper.setText(R.id.tvDescription, item.getDescription()+"");

        Glide.with(mActivity)
                .load(SPUtils.getPrefString(mActivity, Pkey.cnd, Config.default_cnd) + item.getCover())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(mActivity).asGif().load(R.drawable.loading).into(ivCover);
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


        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickLimit.canClick1()) {
                    Intent intent = new Intent(mActivity, DetailComicActivity.class);
                    intent.putExtra("id", item.getComic_id());
                    mActivity.startActivity(intent);
                }
            }
        });


        if (helper.getAdapterPosition()==1){
            tvRank.setBackgroundResource(R.mipmap.top1);
        }else if (helper.getAdapterPosition()==2){
            tvRank.setBackgroundResource(R.mipmap.top2);
        }else if (helper.getAdapterPosition()==3){
            tvRank.setBackgroundResource(R.mipmap.top3);
        }else {
            tvRank.setBackgroundResource(R.mipmap.top4);
        }
        helper.setText(R.id.tvRank,helper.getAdapterPosition()+1+"");

    }
}
