package com.luoshunkeji.comic.network;

import android.content.Context;

import com.android.volley.VolleyError;
import com.luoshunkeji.comic.utils.L;

/**
 * Created by fnuo on 2017/3/15.
 */

public class OkhttpError {
	public static String getMessage(VolleyError error) {
		String result = null;
		if (error != null) {
			if (error.networkResponse != null) {
				switch (error.networkResponse.statusCode) {
					case 400:
						result = "错误请求";// Bad Request
						break;
					case 403:
						result = "禁止访问";// Request Forbidden
						break;
					case 404:
						result = "未找到链接";// HTTP Not Found
						break;
					case 500:
						result = "内部服务器错误";// Internal Server Error
						break;
					case 502:
						result = "无效的网关";// Bad Gateway
						break;
					default:
						// result = "Request error code:"
						// + error.networkResponse.statusCode;
						result = "请求错误代码:" + error.networkResponse.statusCode;
						break;
				}
			} else {
				String str = error.getMessage();
				if (str == null) {
					result = "请求超时";// Request time out
				} else {
					if (str.startsWith("java.net.ConnectException:")) {
						result = "连接超时";// Connect time out
					} else if (str.startsWith("java.lang.RuntimeException:")) {
						result = "错误链接";// Bad URL
					} else if (str.startsWith("java.net.UnknownHostException:")) {
						result = "无法解析域名";// UnknownHost
					} else if (str.startsWith("java.lang.IllegalArgumentException:")) {
						result = "参数错误";// Incorrect param
					} else if (str.startsWith("java.net.SocketException:")) {
						result = "连接失败";// Connect failed
					} else {
						result = str;
					}
				}
			}
		} else {
			result = "OK!";
		}
		return result;
	}

	public static void printError(boolean isToast, Context context, VolleyError error) {
		if (error != null) {
			if (error.networkResponse != null) {
				if (error.networkResponse.statusCode == 500) {
					// 提交错误给后台

				}
			}
		}
		if (isToast) {
//			T.showMessage((context, getMessage(error));
			try {
//				T.showMessage(context, getMessage(error));
				L.e("error-->"+getMessage(error));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
