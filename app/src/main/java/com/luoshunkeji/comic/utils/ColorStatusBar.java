package com.luoshunkeji.comic.utils;

import android.app.Activity;

import androidx.core.content.ContextCompat;

import com.github.zackratos.ultimatebar.UltimateBar;

/**
 * author Administrator
 * date 2018/3/19.
 * desc
 */

public class ColorStatusBar {
    public static void setColorStatusBar(Activity activity, int color) {
        //状态栏字体颜色设置为黑色

        //设置状态栏的颜色
        UltimateBar.newColorBuilder()
                .statusColor(ContextCompat.getColor(activity, color))   // 状态栏颜色
                .applyNav(false)             // 是否应用到导航栏
                .build(activity)
                .apply();
//
    }
}
