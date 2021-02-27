package com.luoshunkeji.comic.dao;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;

import com.luoshunkeji.comic.utils.L;


/**
 * 基本的Activity
 * Test
 */
public abstract class BaseActivity extends Activity implements GUIObserver {
	private static final String TAG = "BaseActivity";

	public abstract void createActivity(Bundle savedInstanceState);

	public abstract void initData();

	public abstract void initView();

	/**
	 * Activity布局完成时启动时，加入Activity列表，最后初始化数据、初始化视图
	 * @return
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		createActivity(savedInstanceState);
		String key = BaseActivity.this.getClass().getName();
		L.i(TAG, "new:" + key);
		Activity act = null;
		try {
			act = (Activity) GUIConcrete.getObserver(BaseActivity.this.getClass());
		} catch (Exception e) {
		}
		if (act != null) {
			// MQLog.i(TAG,"finish old Activity:" + key);
			// act.finish();
		}
		GUIConcrete.addObserver(BaseActivity.this);
//		XAApp.getInstance().addActivity(this);
		initData();
		initView();
		super.onCreate(savedInstanceState);
	}

	/**
	 * Activity退出时，从Activity列表移除
	 * @return
	 */
	@Override
	protected void onDestroy() {
		L.i(TAG, "onDestroy:" + getClass().getName());
		GUIConcrete.removeObserver(getClass());
		super.onDestroy();
	}

	/**
	 * 关闭一个已启动的Activity
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
		GUIConcrete.notifyData(data);
	}

	/**
	 * 不做任何事，只是用于避免子类都强制实现改方式
	 */
	@Override
	public void OnDataUpdate(Object data) {
	}

	// 手指向右滑动时的最小速度
	private static final int XSPEED_MIN = 200;// (200px/ms)*100
	// 手指向右滑动时的最小距离
	private static final int XDISTANCE_MIN = 200;// 滑动170像素
	private float xDown;
	private float xMove;
	private long xstarttime;
	private long xuptime;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			xDown = event.getRawX();
//			xstarttime = System.currentTimeMillis();
//			break;
//		case MotionEvent.ACTION_MOVE:
//		case MotionEvent.ACTION_UP:
//			xMove = event.getRawX();
//			if (xMove > xDown) {
//				xuptime = System.currentTimeMillis();
//				float distanceX = xMove - xDown;
//				float xSpeed = distanceX / (xuptime - xstarttime) * 100;
//				if (distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
//					if (getClass().equals(MainActivity.class)) {
//						// 首页取消滑动退出
//						storeback false;
//					} else {
//						finish();
//					}
//				}
//			}
//			break;
		default:
			break;
		}
		return false;
	}

}
