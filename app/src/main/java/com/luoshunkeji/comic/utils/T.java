package com.luoshunkeji.comic.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luoshunkeji.comic.R;


public class T {
    private static Toast toast;

    /**
     * Hide the toast, if any.
     */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

    /**
     * 显示自定义Toast提示(来自res)
     **/
    public static void showMessage(Context context, int resId) {
        showMessage(context, context.getString(resId));
    }

    /**
     * 显示自定义Toast提示(来自String)
     **/
    public static void showMessage(final Context context, final String text) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = new Toast(context);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                    }
                    LinearLayout toastLayout = new LinearLayout(context);
                    toastLayout.setOrientation(LinearLayout.HORIZONTAL);
                    toastLayout.setPadding(DensityUtil.dip2px(15), DensityUtil.dip2px(12), DensityUtil.dip2px(15), DensityUtil.dip2px(12));
                    toastLayout.setBackgroundResource(R.drawable.bg_toast);
                    toastLayout.setGravity(Gravity.CENTER);
                    TextView message = new TextView(context);
                    message.setGravity(Gravity.CENTER);
                    message.setTextColor(context.getResources().getColor(android.R.color.white));
                    message.setTextSize(12);
                    toastLayout.addView(message);
                    message.setText(text);
                    toast.setView(toastLayout);
                    toast.show();
                }
            });
        } else {
            if (toast == null) {
                toast = new Toast(context);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            LinearLayout toastLayout = new LinearLayout(context);
            toastLayout.setOrientation(LinearLayout.HORIZONTAL);
            toastLayout.setPadding(DensityUtil.dip2px(15), DensityUtil.dip2px(12), DensityUtil.dip2px(15), DensityUtil.dip2px(12));
            toastLayout.setBackgroundResource(R.drawable.bg_toast);
            toastLayout.setGravity(Gravity.CENTER);
            TextView message = new TextView(context);
            message.setGravity(Gravity.CENTER);
            message.setTextColor(context.getResources().getColor(android.R.color.white));
            message.setTextSize(15);
            toastLayout.addView(message);
            message.setText(text);
            toast.setView(toastLayout);
            toast.show();
        }
    }
}
