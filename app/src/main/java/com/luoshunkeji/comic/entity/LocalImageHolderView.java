package com.luoshunkeji.comic.entity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.luoshunkeji.comic.MyApplication;
import com.luoshunkeji.comic.utils.CornerTransform;
import com.luoshunkeji.comic.utils.DensityUtil;

/**
 * Created by Sai on 15/8/4.
 * 本地图片Holder例子
 */
public class LocalImageHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {

        imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        //设置图片圆角角度

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setAdjustViewBounds(true);
//        android:adjustViewBounds="true"

//        CornerTransform transformation = new CornerTransform(MyApplication.getContext(), DensityUtil.dip2px(4));
//
////        设置为false的是需要设置圆角的
//        transformation.setExceptCorner(true, true, false, false);
        Glide.with(MyApplication.getContext()).load(data).
//                skipMemoryCache(true).
//                diskCacheStrategy(DiskCacheStrategy.RESOURCE).
//                transform(transformation).

                into(imageView);


    }
}
