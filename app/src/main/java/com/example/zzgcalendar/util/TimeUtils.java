package com.example.zzgcalendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static String dateFormat_day = "HH:mm";
    public static String dateFormat_month = "MM-dd";
    public final static String mFormat = "yyyy-MM-dd";

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToString(long time) {
        return dateToString(time, "MM-dd HH:mm");
    }

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToTime(long time) {
        try {
            return dateToString(time, "YYYY-MM-dd HH:mm");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToTime(String time) {
        try {
            long date = Long.parseLong(time);
            return dateToString(date, "YYYY-MM-dd HH:mm");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToTimeComplete(long time) {
        return dateToString(time, "YYYY-MM-dd HH:mm:ss");
    }

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String longTimeToDateDay(long time) {
        return dateToString(time, "MM-dd");
    }

    /**
     * 时间转换成字符串,指定格式
     *
     * @param time   时间
     * @param format 时间格式
     */
    public static String dateToString(long time, String format) {
        try {
            Date date = new Date(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取2个字符日期的天数差
     *
     * @param p_startDate
     * @param p_endDate
     * @return 天数差
     */
    public static long getDaysOfTowDiffDate(String p_startDate, String p_endDate) {

        Date l_startDate = TimeUtils.strToDate(TimeUtils.mFormat, p_startDate);
        Date l_endDate = TimeUtils.strToDate(TimeUtils.mFormat, p_endDate);
        long l_startTime = l_startDate.getTime();
        long l_endTime = l_endDate.getTime();
        long betweenDays = (long) ((l_endTime - l_startTime) / (1000 * 60 * 60 * 24));
        return betweenDays;
    }


    /**
     * 将字符串时间改成Date类型
     *
     * @param format
     * @param dateStr
     * @return
     */
    public static Date strToDate(String format, String dateStr) {

        Date date = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    /**
     * 获取任意时间的下一个月
     * 描述:<描述函数实现的功能>.
     *
     * @param repeatDate
     * @return
     */
    public static String getPreMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取任意时间的上一个月
     * 描述:<描述函数实现的功能>.
     * <p>
     * 2018.8为最早时间
     *
     * @param repeatDate
     * @return
     */
    public static String getLastMonth(String repeatDate) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        int year = Integer.parseInt(repeatDate.substring(0, 4));
        String monthsString = repeatDate.substring(5, 7);
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }

        if (year <= 2018 && month <= 8) {
            return repeatDate;
        }
        cal.set(year, month - 2, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }


    /**
     * 根据年月日拼成   YYYY-MM-DD HH   的格式
     *
     * @param yearDate
     * @param monthDate
     * @param dayDate
     * @param hourDate
     * @return
     */
    public static String getDateStrForYMDH(int yearDate, int monthDate, int dayDate, int hourDate) {
        String date = new StringBuffer().append(yearDate).append("-")
                .append(monthDate < 10 ? "0" + monthDate
                        : monthDate).append("-")
                .append(dayDate < 10 ? "0" + dayDate
                        : dayDate).append(" ")
                .append(hourDate < 10 ? "0" + hourDate
                        : hourDate)
                .toString();
        return date;
    }

}