package com.luoshunkeji.comic.dao;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.luoshunkeji.comic.utils.L;


/**
 * 基本的Fragment
 */
public abstract class BaseFragment extends Fragment implements GUIObserver {
    private static final String TAG = "BaseFragment";

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void initData();

    public abstract void initView();

    /**
     * Fragment创建时调用的获取视图方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState);

        String key = BaseFragment.this.getClass().getName();
        L.i(TAG, "new:" + key);
        GUIConcrete.addObserver(BaseFragment.this);

        initView();
        initData();

        return view;
    }
    public boolean isPermissionGranted(String permission) {
        if (getContext() == null) {
            return false;
        }

        return ActivityCompat.checkSelfPermission(getContext(), permission ) == PackageManager.PERMISSION_GRANTED;
    }



    /**
     * Activity退出时，从Activity列表移除
     *
     * @return
     */
    @Override
    public void onDestroy() {
        L.i(TAG, "onDestroy:" + getClass().getName());
        GUIConcrete.removeObserver(getClass());
        super.onDestroy();
    }

    /**
     * 通知所有已存在的activity更新数据
     */
    @Override
    public void notifyData(Object data) {
        GUIConcrete.notifyData(data);
    }

    /**
     * 不做任何事，只是用于避免子类都强制实现改方式
     */
    @Override
    public void OnDataUpdate(Object data) {
    }

}
