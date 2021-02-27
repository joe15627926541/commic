package com.luoshunkeji.comic.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * 屏幕获取工具
 * 
 * @author LinPH
 * @date 2014-6-23 下午3:22:52
 */
public class ScreenUtil {
	public static DisplayMetrics metric = new DisplayMetrics();

	/**
	 * 得到物理屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity context) {

		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}

	/**
	 * 得到物理屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity context) {

		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}


	/**
	 * 得到通知栏高度 注意：不能插入到Activity的onCreate()、onStart()和onResume()函数中，否则得到的值为0
	 * 
	 * @param context
	 * @return
	 */
	public static int getStateHeight(Context context) {
		/*Rect frame = new Rect();
		content.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		return frame.top;*/
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 屏幕截图
	 * @param activity
	 * @return
	 */
	public static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 去掉标题栏
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

}
