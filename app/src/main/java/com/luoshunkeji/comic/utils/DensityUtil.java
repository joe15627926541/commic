package com.luoshunkeji.comic.utils;

import android.content.Context;
import android.view.View;

import com.luoshunkeji.comic.MyApplication;


/**
 * @author supernan 屏幕适配工具
 */
public class DensityUtil {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int dip2px(float dpValue) {
		float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale =  context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	/**
	 * 获取控件的高度
	 */
	public static int getViewMeasuredHeight(View view) {
		calculateViewMeasure(view);
		return view.getMeasuredHeight();
	}

	/**
	 * 获取控件的宽度
	 */
	public static int getViewMeasuredWidth(View view) {
		calculateViewMeasure(view);
		return view.getMeasuredWidth();
	}

	/**
	 * 测量控件的尺寸
	 */
	private static void calculateViewMeasure(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

		view.measure(w, h);
	}
}
