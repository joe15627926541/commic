package com.luoshunkeji.comic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/6/22.
 */
public class NumUtil {
    /**
     * 这2个方法，这个方法我写在工具类里的，
     * 作用是保留2位小数，一个返回String一个返回float
    */
    public static String NumberFormat(float f, int m){
        return String.format("%."+m+"f",f);
    }

    public static float NumberFormatFloat(float f,int m){
        String strfloat = NumberFormat(f,m);
        return Float.parseFloat(strfloat);
    }

    public static String findNumber(String num){

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(num);
        return m.replaceAll("").trim();
    }


}
