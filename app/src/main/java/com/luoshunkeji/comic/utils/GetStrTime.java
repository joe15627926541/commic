package com.luoshunkeji.comic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017/6/9.
 */
public class GetStrTime {
    //时间戳转字符串，年月日
    public static String getStrTimeYMD(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }


    //时间戳转字符串，时分秒
    public static String getStrTimeHSM(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }
    //时间戳转字符串，时分
    public static String getStrTimeHS(long timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            date = sdf.format(new Date(timeStamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }
    //时间戳转字符串，时分
    public static String getStrTimeMD(long timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 ");
            date = sdf.format(new Date(timeStamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getStrTimeMD1(long timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd ");
            date = sdf.format(new Date(timeStamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }
    //时间戳转字符串，时分
    public static String getStrTimeYMD(long timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(new Date(timeStamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    //时间戳转字符串，年月日时分秒
    public static String getStrTimeYMDHSM(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    //时间戳转字符串，年月日时分
    public static String getStrTimeYMDHS(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }//时间戳转字符串，秒
    //时间戳转字符串，年月日时分
    public static String getStrTimeYMDHS(long timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.format(new Date(timeStamp));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }//时间戳转字符串，秒

    public static String getStrTimeD(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    //时间戳转字符串，时
    public static String getStrTimeH(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    //时间戳转字符串，分
    public static String getStrTimeM(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }

    //时间戳转字符串，秒
    public static String getStrTimeS(String timeStamp) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ss");
            Long time = Long.parseLong(timeStamp);
            date = sdf.format(new Date(time * 1000L));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getStrTimeYMD(Date date1) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(date1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static int getYear(Date date1) {
        int  date = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            date = Integer.parseInt(sdf.format(date1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
