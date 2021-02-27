package com.luoshunkeji.comic.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

/**
 * Created by user on 2016/11/24.
 */
public class StringTextUtil {
    /**
     * 格式化数字（实数，非实数都默认转成0.00显示）
     *
     * @param text 只能传数字
     * @return
     */
    public static SpannableString formatTextNumString(String text) {
        if (text == null || text.equals("")) {//为空
            return formatTextNumString("0.00");
        }
        if (!Pattern.compile("^[-+]?\\d+(\\.\\d+)?$").matcher(text).matches()) {//不是实数（带字母或者汉字）
            return formatTextNumString("0.00");
        }

        //设置文本大小
        SpannableString spanText = new SpannableString(text);
        if (text.contains(".")) {
            String[] list = text.split("\\.");
            if (list[0] != null) {//整数字体较大
                spanText.setSpan(new AbsoluteSizeSpan(15, true), 0, list[0].length(),
                        SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (list[1] != null && !"".equals(list[1])) {//小数字体较小
                spanText.setSpan(new AbsoluteSizeSpan(11, true), list[0].length(), text.length(),
                        SPAN_INCLUSIVE_EXCLUSIVE);
            } else {//如：12. ，就补零回调
                return formatTextNumString(text + "00");
            }

        } else {//整数
            text = text + ".00";
            return formatTextNumString(text);
        }


        //设置文本颜色
        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#ff464e")), 0, spanText.length(),
                SPAN_INCLUSIVE_EXCLUSIVE);

        //字体（正常）
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        spanText.setSpan(span, 0, spanText.length(),
                SPAN_INCLUSIVE_EXCLUSIVE);

        return spanText;
    }

    public static SpannableString sapntext(String text, int start, int end, int color) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, start, end, SPAN_INCLUSIVE_EXCLUSIVE);
        //放大数字
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.6f);
        spannableString.setSpan(sizeSpan, start, end, SPAN_INCLUSIVE_EXCLUSIVE);
        //加粗
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan_B, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    //复制淘宝商品标题淘口令等 截取[] ()内关键字
    public static String getClipdata(String msg) {
        if (msg.contains("【") && msg.contains("】")) {
            int beginIndex_str = msg.indexOf("【") + 1;
            int endinTndex_str = msg.indexOf("】");
            String substring_str = msg.substring(beginIndex_str, endinTndex_str);
            if ((substring_str.contains("(") && substring_str.contains(")"))) {
                int one = substring_str.indexOf("(") + 1;
                int two = substring_str.indexOf(")");
                msg = substring_str.substring(one, two);
                if (!msg.equals("")) {
                    return msg;
                }
            }
            if ((substring_str.contains("（") && substring_str.contains("）"))) {
                int one = substring_str.indexOf("（") + 1;
                int two = substring_str.indexOf("）");
                msg = substring_str.substring(one, two);
                if (!msg.equals("")) {
                    return msg;
                }
            }
            return substring_str;

        }
        return msg;
    }

    /**
     * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
     */
    public static SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString spannableString = new SpannableString(text);
        //条件 keyword
        Pattern pattern = Pattern.compile(keyword);
        //匹配
        Matcher matcher = pattern.matcher(new SpannableString(text.toLowerCase()));
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然也只能是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }

    //处理省州县的名称（去除市，县等）
    public static String ObtainCityName(String city) {
        String shortCity = "深圳";
        if (!city.contains("市") && !city.contains("盟") && !city.contains("自治州") && !city.contains("地区") && !city.contains("林区") && !city.contains("群岛") &&!city.contains("县")){
            return city;
        }
        if (city.contains("市")) {
            shortCity = city.substring(0, city.indexOf("市"));
        } else if (city.contains("盟")) {
            shortCity = city.substring(0, city.indexOf("盟"));
        }else if (city.contains("自治州")) {
            shortCity = city.substring(0, city.indexOf("自治州"));
        }else if (city.contains("地区")) {
            shortCity = city.substring(0, city.indexOf("地区"));
        }else if (city.contains("林区")) {
            shortCity = city.substring(0, city.indexOf("林区"));
        }else if (city.contains("群岛")) {
            shortCity = city.substring(0, city.indexOf("群岛"));
        }else if (city.contains("县")) {
            shortCity = city.substring(0, city.indexOf("县"));
        }

        return shortCity;
    }

}
