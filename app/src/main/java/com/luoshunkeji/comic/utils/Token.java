package com.luoshunkeji.comic.utils;

import android.content.Context;

import com.luoshunkeji.comic.MyApplication;


public class Token {
	public static String getToken(Context context) {
		return SPUtils.getPrefString(context, Pkey.token, "");
	}
	public static String getNewToken(){
		return SPUtils.getPrefString(MyApplication.getContext(), Pkey.token, "");
	}
	public static boolean isLogin(){
		if (SPUtils.getPrefString(MyApplication.getContext(),Pkey.token,"").equals("")){
			return false;
		}else{
			return true;
		}
	}
}
