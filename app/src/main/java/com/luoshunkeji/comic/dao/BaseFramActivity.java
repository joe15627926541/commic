package com.luoshunkeji.comic.dao;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.zackratos.ultimatebar.UltimateBar;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.me.BindPhoneActivity;
import com.luoshunkeji.comic.me.ChangeAccountActivity;
import com.luoshunkeji.comic.me.ComplanitActivity;
import com.luoshunkeji.comic.me.FeedBackActivity;
import com.luoshunkeji.comic.me.FindAccountActivity;
import com.luoshunkeji.comic.me.OrderActivity;
import com.luoshunkeji.comic.me.PasswordUpdateActivity;
import com.luoshunkeji.comic.me.ReadHistoryActivity;
import com.luoshunkeji.comic.me.TopupActivity;
import com.luoshunkeji.comic.me.WelfareCneterActivity;
import com.luoshunkeji.comic.read.DetailComicActivity;
import com.luoshunkeji.comic.read.ReadActivity;
import com.luoshunkeji.comic.utils.L;

import static com.luoshunkeji.comic.MyApplication.getContext;


/**
 * 基本的FragmentActivity FragmentActivity
 */
public abstract class BaseFramActivity extends AppCompatActivity implements GUIObserver {//, TencentLocationListener
    private static final String TAG = "BaseFramActivity";

    public abstract void createActivity(Bundle savedInstanceState);

    public abstract void initData();

    public abstract void initView();

    /**
     * Activity布局完成时启动时，加入Activity列表，最后初始化数据、初始化视图
     *
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        createActivity(savedInstanceState);
        Activity act = null;
        try {
            act = (Activity) GUIConcrete.getObserver(BaseFramActivity.this.getClass());
        } catch (Exception e) {
        }
        if (act != null) {

        }

        GUIConcrete.addObserver(BaseFramActivity.this);
        L.i(TAG, "closeActivity:111" + act);
        initView();
        initData();
    }


    /**
     * Activity退出时，从Activity列表移除
     *
     * @return
     */
    @Override
    protected void onDestroy() {
        L.i(TAG, "onDestroy:" + getClass().getName());
        GUIConcrete.removeObserver(getClass());
        super.onDestroy();
    }

    /***
     * 关闭所有已启动的Activity
     *
     * */
    public static void closeAllActivity() {

        GUIConcrete.removeAll();

    }


    /**
     * 关闭一个已启动的Activity
     *
     * @return
     */
    public static boolean closeActivity(Class clas) {
        L.i(TAG, "closeActivity:" + clas.getName());
        boolean result = false;
        Activity activity = null;
        try {
            activity = (Activity) GUIConcrete.getObserver(clas);
        } catch (Exception e) {
        }

        if (activity != null) {
            activity.finish();
            GUIConcrete.removeObserver(clas);
            result = true;
        }
        return result;
    }

    /**
     * 获取已启动的Activity
     *
     * @return
     */
    public static Activity getActivity(Class clas) {
        Activity activity = null;
        try {
            activity = (Activity) GUIConcrete.getObserver(clas);
        } catch (Exception e) {
        }
        return activity;
    }

    /**
     * 通知所有已存在的activity更新数据
     */
    @Override
    public void notifyData(Object data) {
//		GUIConcrete.notifyData(data);
    }

    public boolean isPermissionGranted(String permission) {
        if (getContext() == null) {
            return false;
        }

        return ActivityCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 不做任何事，只是用于避免子类都强制实现改方式
     */
    @Override
    public void OnDataUpdate(Object data) {
    }

    protected void setStatusBar() {
        /**
         * 1.使状态栏透明并使contentView填充到状态栏
         * 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。
         *
         * */
        //设置状态栏的颜色
        UltimateBar.newColorBuilder()
                .statusColor(ContextCompat.getColor(this, R.color.black))   // 状态栏颜色
                .applyNav(false)             // 是否应用到导航栏
                .build(this)
                .apply();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );
    }



    @Override
    protected void onResume() {
        super.onResume();
    }


}
