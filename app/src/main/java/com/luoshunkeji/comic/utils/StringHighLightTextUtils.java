package com.luoshunkeji.comic.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/15.
 */

public class StringHighLightTextUtils {
    /**
     * 关键字高亮显示
     * @param text  需要显示的文字
     * @param target 需要高亮的关键字
     * @param color  高亮颜色
     * @param start  头部增加高亮文字个数
     * @param end   尾部增加高亮文字个数
     * @return 处理完后的结果
     */
    public static SpannableString highlight(String text, String target,
                                            String color, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
            spannableString.setSpan(span, matcher.start() - start, matcher.end() + end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
    public static String regNum(String string){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return  m.replaceAll("").trim();
    }


}
