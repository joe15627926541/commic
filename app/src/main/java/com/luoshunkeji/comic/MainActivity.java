package com.luoshunkeji.comic;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.fragment.HomeFragment;
import com.luoshunkeji.comic.fragment.MeFragment;
import com.luoshunkeji.comic.fragment.RecommendFragment;
import com.luoshunkeji.comic.utils.ClickLimit;
import com.luoshunkeji.comic.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends BaseFramActivity {


    private long exitTime = 0;
    private boolean initdone;
    public static Fragment[] fragments;
    private int[] tabIds = {R.id.tab_home,
            R.id.tab_recommend, R.id.tab_me};
    private static RadioButton[] radioBtns;
    private static FragmentManager fmanger;

    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        fragments = new Fragment[3];
        fragments[0] = new HomeFragment();
        fragments[1] = new RecommendFragment();
        fragments[2] = new MeFragment();
        fmanger = getSupportFragmentManager();
        radioBtns = new RadioButton[tabIds.length];
        FragmentTransaction ft = fmanger.beginTransaction();
        ft.add(R.id.layout_fragm, fragments[0], "0");
        for (int i = 0; i < fragments.length; i++) {
            radioBtns[i] = (RadioButton) findViewById(tabIds[i]);
            if (i == 0) {
                ft.show(fragments[i]);
            } else {
                ft.hide(fragments[i]);
            }
        }
        ft.commitAllowingStateLoss();
        initdone = true;

    }

    public void fragmclick(View view) {
        if (!initdone || !ClickLimit.canClick(200)) {
            return;
        }
        int id = view.getId();
        FragmentTransaction framtran = fmanger.beginTransaction();
        for (int i = 0; i < tabIds.length; i++) {
            if (tabIds[i] == id) {
                if (!fragments[i].isAdded()) {
                    try {
                        framtran.add(R.id.layout_fragm, fragments[i], i + "");// 点击的时候才add，不需要一次性加载，让app启动更快一些
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                framtran = framtran.show(fragments[i]);
                radioBtns[i].setChecked(true);
            } else {
                framtran = framtran.hide(fragments[i]);
                radioBtns[i].setChecked(false);
            }
        }
        framtran.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            ToastUtil.showToast("再按一次退出程序");
        } else {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String taget) {
        if (taget.equals("select_recommend_fragment")) {
            FragmentTransaction framtran = fmanger.beginTransaction();
            for (int i = 0; i < tabIds.length; i++) {
                if (tabIds[i] == R.id.tab_recommend) {
                    if (!fragments[i].isAdded()) {
                        try {
                            framtran.add(R.id.layout_fragm, fragments[i], i + "");// 点击的时候才add，不需要一次性加载，让app启动更快一些
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    framtran = framtran.show(fragments[i]);
                    radioBtns[i].setChecked(true);
                } else {
                    framtran = framtran.hide(fragments[i]);
                    radioBtns[i].setChecked(false);
                }
            }
            framtran.commitAllowingStateLoss();
        } else if (taget.equals("select_home_fragment")) {
            FragmentTransaction framtran = fmanger.beginTransaction();
            for (int i = 0; i < tabIds.length; i++) {
                if (tabIds[i] == R.id.tab_home) {
                    if (!fragments[i].isAdded()) {
                        try {
                            framtran.add(R.id.layout_fragm, fragments[i], i + "");// 点击的时候才add，不需要一次性加载，让app启动更快一些
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    framtran = framtran.show(fragments[i]);
                    radioBtns[i].setChecked(true);
                } else {
                    framtran = framtran.hide(fragments[i]);
                    radioBtns[i].setChecked(false);
                }
            }
            framtran.commitAllowingStateLoss();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
