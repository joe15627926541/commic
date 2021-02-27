package com.luoshunkeji.comic.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.luoshunkeji.comic.entity.Comics;
import com.luoshunkeji.comic.utils.DensityUtil;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.ScreenUtil;

import java.util.List;

public class RankAdapter extends BaseQuickAdapter<Comics, BaseViewHolder> {
    private Activity mActivity;
    private List<Comics> list;
    private final Handler handler = new Handler();

    public RankAdapter(Activity activity, int layoutResId, @Nullable List<Comics> data) {
        super(layoutResId, data);
        list = data;
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, Comics item) {
        LinearLayout llItem = helper.getView(R.id.llItem);
        final ImageView ivCover = helper.getView(R.id.ivCover);
        ViewGroup.LayoutParams lp = llItem.getLayoutParams();
        lp.width = (ScreenUtil.getScreenWidth(mActivity) - DensityUtil.dip2px(21)) / 3;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        llItem.setLayoutParams(lp);
        helper.addOnClickListener(R.id.llItem);
        helper.setText(R.id.tvName, item.getName());


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

    }
}
