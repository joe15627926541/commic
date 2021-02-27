package com.luoshunkeji.comic.utils;

import android.widget.Toast;

import com.luoshunkeji.comic.MyApplication;


/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast
 */


/***
 *
 * 由于产品项目希望吐司都在页面居中展示，并且控制字体大小，所以统一调用
 *
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
//        if (mToast == null) {
//            mToast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
//        }
//        mToast.setText(text);
//        mToast.show();
        T.showMessage(MyApplication.getContext(), text);
    }

}
