package com.luoshunkeji.comic.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/30.
 * 应用签名信息
 */

public class AppSigning {
    public final static String MD5    = "MD5";
    public final static String SHA1   = "SHA1";
    public final static String SHA256 = "SHA256";

    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param context
     * @param packageName
     * @param type
     *
     * @return
     */
    public static ArrayList<String> getSingInfo(Context context, String packageName, String type)
    {
        ArrayList<String> result = new ArrayList<String>();
        try
        {
            Signature[] signs = getSignatures(context, packageName);
            for (Signature sig : signs)
            {
                String tmp = "error!";
                if (MD5.equals(type))
                {
                    tmp = getSignatureString(sig, MD5);
                }
                else if (SHA1.equals(type))
                {
                    tmp = getSignatureString(sig, SHA1);
                }
                else if (SHA256.equals(type))
                {
                    tmp = getSignatureString(sig, SHA256);
                }
                result.add(tmp);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回对应包的签名信息
     *
     * @param context
     * @param packageName
     *
     * @return
     */
    public static Signature[] getSignatures(Context context, String packageName)
    {
        PackageInfo packageInfo = null;
        try
        {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     *
     * @param sig
     * @param type
     *
     * @return
     */
    public static String getSignatureString(Signature sig, String type)
    {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try
        {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null)
            {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for (byte digestByte : digestBytes)
                {
                    sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprint = sb.toString();
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return fingerprint;
    }
    //字符串替换操作
    public static String GetStringReplace(String s){
        String result="";
//        1、startsWith(String prefix)
//        该方法用于判断当前字符串对象的前缀是否是参数指定的字符串。
//        2、endsWith(String suffix)
//        该方法用于判断当前字符串是否以给定的子字符串结束
        try {
            if (s.startsWith("[") && s.endsWith("]")){
                String s1 = s.replace("[","");
                String s2 = s1.replace("]","");
                result = s2;
            }else {
                result=s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
