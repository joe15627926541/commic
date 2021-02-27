package com.luoshunkeji.comic.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sign {
	public static String appkey = "123";

	public final static String getSign() {
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1){
//		String sign_key="";
//		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
//	    List<String> list=new ArrayList<String>();
//	    list.add("time"+time);
//	    list.add(arg1);
//	    Collections.sort(list);
//		for (String temp:list) {
//			sign_key=sign_key+temp;
//		}
		return Md5.MD5(appkey  +arg1+ appkey);
	}
	public final static String getSign(String arg1, String arg2){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3, String arg4){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    list.add(arg4);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3, String arg4, String arg5){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    list.add(arg4);
	    list.add(arg5);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    list.add(arg4);
	    list.add(arg5);
	    list.add(arg6);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    list.add(arg4);
	    list.add(arg5);
	    list.add(arg6);
	    list.add(arg7);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    list.add(arg4);
	    list.add(arg5);
	    list.add(arg6);
	    list.add(arg7);
	    list.add(arg8);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	public final static String getSign(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9){
		String sign_key="";
		String time = Long.toString(System.currentTimeMillis()).substring(0, 10);
	    List<String> list=new ArrayList<String>();
	    list.add("time"+time);
	    list.add(arg1);
	    list.add(arg2);
	    list.add(arg3);
	    list.add(arg4);
	    list.add(arg5);
	    list.add(arg6);
	    list.add(arg7);
	    list.add(arg8);
	    list.add(arg9);
	    Collections.sort(list);
		for (String temp:list) {
			sign_key=sign_key+temp;
		}
		return Md5.MD5(appkey  +sign_key+ appkey);
	}
	
}
