package com.luoshunkeji.comic.utils;

/**
 * 点击限制工具类(默认0.3秒内不能在次点击)
 * 
 * @author ping
 * @create 2014-5-17 下午6:36:32
 */
public class ClickLimit {
	private static long lastClickTime = 0;

	public static boolean canClick() {
		return canClick(1000);
	}
	public static boolean canClick1() {
		return canClick(200);
	}

	public static boolean canClick(int interval) {
		if ((System.currentTimeMillis() - lastClickTime) > interval) {
			lastClickTime = System.currentTimeMillis();
			return true;
		} else {
			return false;
		}
	}

}
