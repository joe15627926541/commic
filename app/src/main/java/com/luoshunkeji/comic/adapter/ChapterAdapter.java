package com.luoshunkeji.comic.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

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
import com.luoshunkeji.comic.entity.DetailComicEntity;
import com.luoshunkeji.comic.utils.ImageUtils;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;

import java.util.List;

public class ChapterAdapter extends BaseQuickAdapter<DetailComicEntity.ChapterListBean, BaseViewHolder> {
    private Activity mActivity;
    private List<DetailComicEntity.ChapterListBean> list;
    private final android.os.Handler handler = new Handler();

    public ChapterAdapter(Activity activity, int layoutResId, @Nullable List<DetailComicEntity.ChapterListBean> data) {
        super(layoutResId, data);
        list = data;
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailComicEntity.ChapterListBean item) {
        final ImageView ivCover = helper.getView(R.id.ivCover);
        ImageView ivIcon = helper.getView(R.id.ivIcon);
        helper.addOnClickListener(R.id.rlItem);
        ImageUtils.setImage(mActivity, SPUtils.getPrefString(mContext, Pkey.cnd, Config.default_cnd) + item.getCover(), ivCover);


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
        helper.setText(R.id.tvTitle, item.getName());
        helper.setText(R.id.tvPrice, item.getPrice() + "");
        if (item.getPrice() > 0) {
            ivIcon.setImageResource(R.mipmap.mine_coin);
        } else {
            ivIcon.setImageResource(R.mipmap.mine_coin_free);
        }
    }
}
