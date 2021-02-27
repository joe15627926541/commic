package com.luoshunkeji.comic.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class SystemTime {
    public static String getTime() {
        return Long.toString(System.currentTimeMillis()).substring(0, 10);
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param milliseconds 传入的 时间戳
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(long milliseconds) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date =new Date(milliseconds);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param milliseconds 传入的 时间戳
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(long milliseconds) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date =new Date(milliseconds);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

}
