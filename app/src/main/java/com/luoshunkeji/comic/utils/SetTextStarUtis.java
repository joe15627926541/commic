package com.luoshunkeji.comic.utils;

import android.text.TextUtils;

/**
 * Created by user on 2017/7/10.
 */
public class SetTextStarUtis {
    public static String setTexStarPhoneandQq(String pNumber){
        String str = null;
        try {
            if(!TextUtils.isEmpty(pNumber) && pNumber.length() > 6 ){
                StringBuilder sb  =new StringBuilder();
                for (int i = 0; i < pNumber.length(); i++) {
                    char c = pNumber.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
                str = sb.toString();
            }else {
                str = pNumber;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
