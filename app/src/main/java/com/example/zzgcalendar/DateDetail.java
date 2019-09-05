package com.example.zzgcalendar;

import java.io.Serializable;

/**
 * Created by admin on 2019/2/21.
 * <p>
 * 时间
 */
public class DateDetail implements Serializable {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setDate(int year, int month, int day, int hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    public String getDate() {
        String date = new StringBuffer().append(year).append("-")
                .append(month < 10 ? "0" + month
                        : month).append("-")
                .append(day < 10 ? "0" + day
                        : day).append(" ")
                .append(hour < 10 ? "0" + hour
                        : hour)
                .toString();
        return date;
    }

}
