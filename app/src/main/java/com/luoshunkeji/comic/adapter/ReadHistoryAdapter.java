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
import com.luoshunkeji.comic.entity.ReadHistoryEntity;
import com.luoshunkeji.comic.utils.ImageUtils;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;

import java.util.List;

public class ReadHistoryAdapter extends BaseQuickAdapter<ReadHistoryEntity, BaseViewHolder> {
    private Activity mActivity;
    List<ReadHistoryEntity> mList;
    private final Handler handler = new Handler();

    public ReadHistoryAdapter(Activity activity, int layoutResId, @Nullable List<ReadHistoryEntity> data) {
        super(layoutResId, data);
        mActivity = activity;
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadHistoryEntity item) {
        helper.addOnClickListener(R.id.llItem);
        final ImageView ivCover = helper.getView(R.id.ivCover);

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
        helper.setText(R.id.tvName, item.getName());
        helper.setText(R.id.tvReadHistory, "阅读至-" + item.getRead_chapter());
    }
}
