package com.luoshunkeji.comic.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.WindowManager;


import com.luoshunkeji.comic.MyApplication;
import com.luoshunkeji.comic.R;

import java.util.List;


public class AppUtil {
	/**
	 * 获取软件自身版本号
	 *
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode() {
		int version;
		PackageManager packageManager = MyApplication.getContext().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(MyApplication.getContext()
					.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		version = packInfo.versionCode;
		return version;
	}

	/**
	 * 获取软件自身版本名
	 *
	 * @return 当前应用的版本号
	 */
	public static String getselfVersionName(Context context) {
		String packname = context.getPackageName();
		return getappVersionName(context, packname);
	}


	public static String getappVersionName(Context context, String packageName) {
		try {
			return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取APP崩溃异常报告
	 *
	 * @param ex
	 * @return
	 */
	public static String getCrashReport(Context context, Throwable ex) {
		PackageInfo pinfo = getPackageInfo(context);
		StringBuffer exceptionStr = new StringBuffer();
		// exceptionStr.append("version: " + pinfo.versionName + "(" +
		// pinfo.versionCode + ")\n");
		exceptionStr.append("android:" + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
		exceptionStr.append("Exception:" + ex.getMessage() + "\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString() + "\n");
		}
		return exceptionStr.toString();
	}


	public static String getPackName() {
		return MyApplication.getContext().getPackageName();
	}


	public static String getAppName() {
		return MyApplication.getContext().getResources().getString(R.string.app_name);
	}

	/**
	 * 获取App安装包信息
	 *
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 提出APP自己结束进程 ）
	 *
	 * @return
	 */
	public static void exit() {
		killProcess(android.os.Process.myPid());
	}

	/**
	 * 根据进程id结束进程
	 *
	 * @param Processid
	 */
	public static void killProcess(int Processid) {
		android.os.Process.killProcess(Processid);
	}

	/**
	 * 获取Meta值
	 *
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	public static String getImei(Context context) {
		// 权限：android:name="android.permission.READ_PHONE_STATE" />
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	// 通过PackageManager的api 查询已经安装的apk
	private List<ResolveInfo> loadApps(Context context) {
		List<ResolveInfo> mApps;
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		mApps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
		return mApps;
	}

	/**
	 * 获取SIM卡运营商
	 *
	 * @param context
	 * @return
	 */
	public static String getOperators(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = "";
		String IMSI = tm.getSubscriberId();
		if (IMSI == null || IMSI.equals("")) {
			return operator;
		}
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			operator = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			operator = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			operator = "中国电信";
		}
		return operator;
	}

	/**
	 * 手机型号
	 *
	 * @return
	 */
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 系统版本
	 *
	 * @return
	 */
	public static String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}


	public static int getWindowWidth(Context paramContext)
	{
		return getWindowManager(paramContext).getDefaultDisplay().getWidth();
	}
	public static WindowManager getWindowManager(Context paramContext)
	{
		return (WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE);
	}
}
