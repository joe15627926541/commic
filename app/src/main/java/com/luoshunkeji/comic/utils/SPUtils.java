package com.luoshunkeji.comic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * SharePreference 快速工具类
 *
 * @author ilove000@foxmail.com
 */
public class SPUtils {
    public static String getPrefString(Context context, String key,
                                       final String defaultValue) {
        SecuritySP securitySharedPreference = new SecuritySP(context, "security_prefs", Context.MODE_PRIVATE);
        return securitySharedPreference.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key,
                                     final String value) {
        SecuritySP securitySharedPreference = new SecuritySP(context, "security_prefs", Context.MODE_PRIVATE);
        SecuritySP.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.putString(key, value);
        securityEditor.apply();
    }

    public static boolean getPrefBoolean(Context context, final String key,
                                         final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(
                key);
    }

    public static void setPrefBoolean(Context context, final String key,
                                      final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(Context context, final String key,
                                  final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }

    public static int getPrefInt(Context context, final String key,
                                 final int defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key,
                                    final float value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getPrefFloat(Context context, final String key,
                                     final float defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(Context context, final String key,
                                      final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).commit();
    }

    public static long getPrefLong(Context context, final String key,
                                   final long defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(
            final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }


    public static void setPrefObject(Context context, String key, Object value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String str = ObjectUtil.toString(value);
        Log.i("", "======================str=======" + str);
        settings.edit().putString(key, str).commit();
    }

    public static <T> T getPrefObject(Context context, String key, Class<T> cls) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String str = settings.getString(key, "");
        Log.i("", "======================str=======1111" + str);
        return (T) ObjectUtil.toObject(str);
    }


//    public static void setPrefList(Context context, String key, List<String> ItemList) {
//        String json = new Gson().toJson(ItemList);
//        setPrefString(context, key, json);
//    }
//
//
//    /**
//     * 获取公告List集合
//     */
//    public static List<String> getPrefList(Context context, String key) {
//        List<String> listTemp = new ArrayList<>();
//        try {
//            String json = getPrefString(context, key, "");
//            if (json.equals("")) {
//                return listTemp;
//            }
//            listTemp = new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
//        } catch (Exception e) {
//
//        }
//        return listTemp;
//
//    }
}
