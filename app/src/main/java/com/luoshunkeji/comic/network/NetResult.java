package com.luoshunkeji.comic.network;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.T;

import java.io.IOException;

/**
 * 网络响应
 *
 * @author ping 2014-4-16 上午10:36:09
 */
public class NetResult {

    /**
     * 有提示，网络连接且请求没有错误?
     *
     * @param activity
     * @param success
     * @param object
     * @param error
     * @return
     * @author ping
     * @create 2014-4-19 下午7:05:24
     */
    public static boolean isSuccess(Activity activity, boolean success, String object, VolleyError error) {
        if (success && object != null) {
            JSONObject json = null;
            try {
                json = JSONObject.parseObject(object);
            } catch (Exception e) {
            }
            if (json == null) {
                T.showMessage(activity, "返回数据无效！");
            } else if (json.containsKey("success")) {
                if (json.getInteger("success") == 1) {
                    return true;
                } else if (json.getInteger("success") == 0) {
                    if (json.getString("msg").equals("您的账号在其他终端登陆，请重新登陆！")) {
                        SPUtils.setPrefString(activity, Pkey.token, "");
                    } else if (json.getString("msg").equals("请求过于频繁")) {

                    } else {
                        T.showMessage(activity, json.getString("msg"));
                    }
                } else if (json.containsKey("message")) {
                    T.showMessage(activity, json.getString("message"));
                } else {
                    T.showMessage(activity, "获取数据失败，请重试");
                }
            } else {
                if (json.containsKey("message")) {
                    T.showMessage(activity, json.getString("message"));

                } else {
                    T.showMessage(activity, "获取数据失败，请重试");
                }
            }

            return false;
        } else {
            VolleyErrorMsg.printError(true, activity, error);
        }
        return false;
    }


    public static String getMsg(String object) {
        String result = null;
        if (object == null) {
            result = "网络请求失败";
        } else {
            JSONObject json = null;
            try {
                json = JSONObject.parseObject(object);
            } catch (Exception e) {
            }
            if (json == null) {
                // 网络错误，取到非json数据
            } else if (json.containsKey("message")) {
                try {
                    result = json.getString("message");
                } catch (Exception e) {
                }
            }
        }
        return result == null ? "unknow message" : result;
    }


    //使用OKHTTP时使用
    public static boolean isSuccess3(Activity activity, boolean success, String object, IOException error) {
        if (success && object != null) {
            JSONObject json = null;
            try {
                json = JSONObject.parseObject(object);
            } catch (Exception e) {
            }
            if (json == null) {
                T.showMessage(activity, "返回数据无效！");
            } else if (json.containsKey("code")) {
                if (json.getInteger("code") == 200) {
                    return true;
                }
            } else {
                T.showMessage(activity, "获取数据失败，请重试!!错误编码"+object.toString());
            }
            return false;
        } else {
//			VolleyErrorMsg.printError(true, activity, error);
        }
        return false;
    }

}
