package com.luoshunkeji.comic.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.luoshunkeji.comic.MyApplication;
import com.luoshunkeji.comic.R;

import java.io.File;


/**
 * Created by user on 2016/9/26.
 */
public class ImageUtils {
    public static ImageView setImage(Activity activity, String url, ImageView imageView) {
        Glide.with(MyApplication.getContext()).load(url).dontAnimate().into(imageView);
        return imageView;
    }

    public static void loadImageViewLoding(Activity mContext, String url, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(MyApplication.getContext()).load(url).apply(new RequestOptions().dontAnimate().placeholder(lodingImage).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.IMMEDIATE).error(errorImageView)).into(mImageView);
    }


    public static void loadOriginalImageView(Activity mContext, String url, ImageView mImageView, int lodingImage, int errorImageView) {
//        Glide.with(MyApplication.getContext()).load(url).apply(new RequestOptions().dontAnimate().placeholder(lodingImage).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.IMMEDIATE).error(errorImageView)).into(mImageView);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//关键代码，加载原始大小
                .format(DecodeFormat.PREFER_ARGB_8888)//设置为这种格式去掉透明度通道，可以减少内存占有
                .placeholder(lodingImage)
                .error(errorImageView);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(mImageView);


    }


    public static void loadImageViewLoding(String url, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(MyApplication.getContext()).load(url).apply(new RequestOptions().dontAnimate().placeholder(lodingImage).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.IMMEDIATE).error(errorImageView)).into(mImageView);
    }

    public static void loadImageViewLoding(Context mContext, File file, ImageView mImageView) {
        Glide.with(MyApplication.getContext()).load(file).dontAnimate().into(mImageView);
    }

    public static void loadImageViewLodingWithCache(Activity mContext, String url, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    public static ImageView setImageWithCache(Activity activity, String url, ImageView imageView) {
        Glide.with(MyApplication.getContext()).load(url).priority(Priority.NORMAL).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        return imageView;
    }

    public static void loadGifViewLoding(Activity mContext, String url, ImageView mImageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(url).apply(options).into(mImageView);
    }

}
