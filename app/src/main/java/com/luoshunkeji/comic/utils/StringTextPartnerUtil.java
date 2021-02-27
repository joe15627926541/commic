package com.luoshunkeji.comic.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;

import java.util.regex.Pattern;

/**
 * Created by user on 2016/11/24.
 */
public class StringTextPartnerUtil {
    /**
     * 格式化数字（实数，非实数都默认转成0.00显示）
     * @param text  只能传数字
     * @return
     */
    public static SpannableString formatTextNumString(String text){
        if(text ==null ||text.equals("")){//为空
            return formatTextNumString("0.00");
        }
        if(!Pattern.compile("^[-+]?\\d+(\\.\\d+)?$").matcher(text).matches()){//不是实数（带字母或者汉字）
            return formatTextNumString("0.00");
        }

        //设置文本大小
        SpannableString spanText = new SpannableString(text);
        if(text.contains(".")){
            String[] list = text.split("\\.");
            if(list[0] !=null){//整数字体较大
                spanText.setSpan(new AbsoluteSizeSpan(18, true), 0, list[0].length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if(list[1] !=null && !"".equals(list[1])){//小数字体较小
                spanText.setSpan(new AbsoluteSizeSpan(12, true), list[0].length(), text.length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }else{//如：12. ，就补零回调
                return formatTextNumString(text+"00");
            }

        }else{//整数
            text = text+".00";
            return formatTextNumString(text);
        }



        //设置文本颜色
//        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, spanText.length(),
//                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //字体（正常）
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        spanText.setSpan(span, 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spanText;
    }
}
