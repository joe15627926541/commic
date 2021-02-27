package com.luoshunkeji.comic.dao;

/**
 * 抽象观察者接口
 */
public interface GUIObserver {
	// 通知所有已打开的Activity更新数据
	public void notifyData(Object data);

	// 所有已打开的Activity更新数据时触发的事件
	public void OnDataUpdate(Object data);
}
