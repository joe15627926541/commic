package com.luoshunkeji.comic.network;

import android.content.Context;

import org.json.JSONObject;

/**
 * Http请求回调
 */
public interface HttpRequestListener {
	
	/** 成功后回调jsonObject
	 * @param jsonObject
	 */
	public void onSuccess(JSONObject jsonObject);
	
	/** 失败返回message
	 * @param message
	 * @param code
	 */
	public void onFail(String message);
	
	/**
	 * 未授权
	 */
	public void onUnauthorized(Context context);
}
