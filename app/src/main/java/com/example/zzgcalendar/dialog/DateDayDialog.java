package com.example.zzgcalendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.zzgcalendar.DateDetail;
import com.example.zzgcalendar.R;
import com.example.zzgcalendar.util.ChineseCalendarTwo;
import com.example.zzgcalendar.view.BirthWheelView;
import com.example.zzgcalendar.view.wheelview.BaseWheelView;
import com.example.zzgcalendar.view.wheelview.LunarUtil;
import com.example.zzgcalendar.view.wheelview.NumericWheelAdapter;
import com.example.zzgcalendar.view.wheelview.OnWheelScrollListener;
import com.example.zzgcalendar.view.wheelview.adapter.NumericLunarWheelAdapter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2018/6/19.
 */

public class DateDayDialog {

    private String tag = "DateDayDialog";

    /**
     * 是否是农历
     */
    private boolean isLunar = false;


    /**
     * 农历时间
     */
    private DateDetail lunarDate = new DateDetail();

    /**
     * 开始的年份  从1950年开始
     */
    private final int YEAR_START = 1950;
    /**
     * 阳历时间
     */
    private DateDetail gregorianDate;
    /**
     * 保存日期的年月日时
     */
    private Map<String, Integer> dataMap = new HashMap<>();

    /**
     * 保存今天的日期
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     */
    public void setDataMap(int year, int month, int day, int hour) {
        dataMap.put("year", year);
        dataMap.put("month", month);
        dataMap.put("day", day);
        dataMap.put("hour", hour);
    }

    public String getDataMap() {
        int yearDate = dataMap.get("year");
        int monthDate = dataMap.get("month");
        int dayDate = dataMap.get("day");
        int hourDate = dataMap.get("hour");

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

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private LinearLayout txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private TextView txt_date_type;
    /**
     * 显示title
     */
    private boolean showTitle = false;
    //    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    int screenWidth = 0;
    int screenHeight = 0;
    private LayoutInflater inflater = null;
    private BirthWheelView year;
    private BirthWheelView month;
    private BirthWheelView day;
    private BirthWheelView hour;
//    private BirthWheelView minute;

    View view = null;
//    private String initDate;
//    private String lastDate;

    private DateDialogValue dateDialogValue;

//    public String getInitDate() {
//        return initDate;
//    }
//
//    public void setInitDate(String initDate) {
//        this.initDate = initDate;
//    }

    /**
     * 日期格式 yyyy-MM-dd <默认构造函数>
     */
    public DateDayDialog(Context context, String initDate, DateDialogValue dateDialogValue) {
        this.context = context;
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
//        this.initDate = initDate;
        this.dateDialogValue = dateDialogValue;
//        lastDate = MyTimeUtil.DateToStr(MyTimeUtil.yyyy_MM_dd_HH, new Date());
    }

    public DateDayDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_date, null);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        txt_msg = (LinearLayout) view.findViewById(R.id.txt_msg);
        txt_msg.addView(getDataPick());

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_date_type = (TextView) view.findViewById(R.id.txt_date_type);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        txt_date_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLunarAndGregorianChange();

            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    /**
     * 农历公历切换
     */
    private void setLunarAndGregorianChange() {
        if (isLunar) {
            txt_date_type.setText("农历");
        } else {
            txt_date_type.setText("阳历");
        }
        isLunar = !isLunar;
        txt_msg.removeAllViews();
        txt_msg.addView(getDataPick());
    }

    /**
     * 公历年份数组   阿拉伯数字
     */
    private String[] years;
    /**
     * 农历年份数组   汉字
     */
    private String[] lunarYears;
    /**
     * 月份数组   阿拉伯数字
     */
    private String[] months;
    /**
     * 农历月份数组   汉字
     */
    private String[] lunarMonths;
    /**
     * 日数组   阿拉伯数字
     */
    private String[] days;
    /**
     * 农历日数组  初几 汉字
     */
    private String[] lunarDays;

    private String[] lunarHour;

    private View getDataPick() {
        Calendar c = Calendar.getInstance();
        int norYear = c.get(Calendar.YEAR);

//        int curMinute = mDay;
        if (gregorianDate == null || lunarDate == null) {
            int curYear = c.get(Calendar.YEAR);
            int curMonth = c.get(Calendar.MONTH) + 1;
            int curDate = c.get(Calendar.DAY_OF_MONTH);
            int curHour = c.get(Calendar.HOUR_OF_DAY);
            gregorianDate = new DateDetail();
            gregorianDate.setDate(curYear, curMonth, curDate, curHour);
            lunarDate = new DateDetail();
            lunarDate = ChineseCalendarTwo.sCalendarSolarToLunarToDate(curYear, curMonth, curDate, curHour);
        }

        view = inflater.inflate(R.layout.wheel_date_picker_no_m, null);
        int yearLength = norYear - YEAR_START + 1;
        years = new String[yearLength];
        lunarYears = new String[yearLength];
        for (int i = 0; i < yearLength; i++) {
            int addYear = YEAR_START + i;
            years[i] = addYear + "";
            String yearText = LunarUtil.getLunarNameOfYear(addYear);
            lunarYears[i] = yearText;
        }

        year = (BirthWheelView) view.findViewById(R.id.year);
        month = (BirthWheelView) view.findViewById(R.id.month);
        day = (BirthWheelView) view.findViewById(R.id.day);
        hour = (BirthWheelView) view.findViewById(R.id.time);

        if (isLunar) {
            initLunarDate(lunarDate.getYear());
        } else {
            setGregorianDate(gregorianDate.getYear(), gregorianDate.getMonth());
        }


        year.addScrollingListener(scrollListener);
        month.addScrollingListener(scrollListener);
        day.addScrollingListener(scrollListener);
        hour.addScrollingListener(scrollListener);

        year.setVisibleItems(7);// 设置显示行数
        month.setVisibleItems(7);
        day.setVisibleItems(7);
        hour.setVisibleItems(7);
        if (isLunar) {
            year.setCurrentItem(lunarDate.getYear() - YEAR_START);
            //先看是否有闰月
            int monthLeap = LunarUtil.getMonthLeapByYear(lunarDate.getYear());
            if (monthLeap < 0) {
                //有闰月先处理闰月
                monthLeap = Math.abs(monthLeap);
                if (lunarDate.getMonth() > monthLeap && lunarDate.getMonth() <= 12) {
                    //比闰月的月份大
                    month.setCurrentItem(lunarDate.getMonth());
                } else if (lunarDate.getMonth() > 12) {
                    //闰月的时候
                    month.setCurrentItem(lunarDate.getMonth() - 12);
                } else {
                    //比闰月月份小
                    month.setCurrentItem(lunarDate.getMonth() - 1);
                }
            } else {
                //不是闰月
                month.setCurrentItem(lunarDate.getMonth() - 1);
            }
            day.setCurrentItem(lunarDate.getDay() - 1);
            hour.setCurrentItem(lunarDate.getHour());
        } else {
            year.setCurrentItem(gregorianDate.getYear() - YEAR_START);
            month.setCurrentItem(gregorianDate.getMonth() - 1);
            day.setCurrentItem(gregorianDate.getDay() - 1);
            hour.setCurrentItem(gregorianDate.getHour());
        }


//        minute.setCurrentItem(curMinute);
        return view;
    }

    /**
     * 初始化农历
     */
    private void initLunarDate(int currentYear) {
        if (year == null) {
            return;
        }
        NumericLunarWheelAdapter lunarYearAdapter = new NumericLunarWheelAdapter(context, lunarYears);
        lunarYearAdapter.setLabel("年");
        year.setViewAdapter(lunarYearAdapter);
        year.setCyclic(true);// 是否可循环滑动


        //设置月份

        //当前年份
//        int currentYear = Integer.parseInt(currentYear);
        int monthLength;
//        //是否有闰月
        int monthLeap = LunarUtil.getMonthLeapByYear(currentYear);
        if (monthLeap == 0) {
            monthLength = 12;
            lunarMonths = LunarUtil.lunarMonths;
        } else {
            monthLength = 13;
            lunarMonths = LunarUtil.getLunarMonthsNamesWithLeap(monthLeap);
        }
//        monthLength = 12;
//        lunarMonths = LunarUtil.lunarMonths;
        months = new String[monthLength];
        for (int i = 0; i < monthLength; i++) {
            months[i] = (i + 1) + "";
        }
        NumericLunarWheelAdapter lunarMonthAdapter = new NumericLunarWheelAdapter(context, lunarMonths);
        lunarMonthAdapter.setLabel("月");
        month.setViewAdapter(lunarMonthAdapter);
        month.setCyclic(true);// 是否可循环滑动

        //设置  日

        //当前月份
//        int currentMonth = Integer.parseInt(months[month.getCurrentItem()]);
        int monthPos;
        if (lunarDate.getMonth() > 12) {
            monthPos = lunarDate.getMonth() - 12 + 1;
        } else {
            monthPos = lunarDate.getMonth();
        }
        int dayLength = LunarUtil.getSumOfDayInMonthForLunarByMonthSway(currentYear, monthPos);
//        int dayLength = 28;
        lunarDays = new String[dayLength];
        days = new String[dayLength];
        for (int i = 0; i < dayLength; i++) {
            lunarDays[i] = LunarUtil.getLunarNameOfDay(i + 1);
            days[i] = i + "";
        }
        NumericLunarWheelAdapter lunarDayAdapter = new NumericLunarWheelAdapter(context, lunarDays);
        lunarDayAdapter.setLabel("日");
        day.setViewAdapter(lunarDayAdapter);
        day.setCyclic(true);// 是否可循环滑动


        //设置  时

        lunarHour = LunarUtil.lunarHour;
        NumericLunarWheelAdapter lunarHourAdapter = new NumericLunarWheelAdapter(context, lunarHour);
        lunarHourAdapter.setLabel("时");
        hour.setViewAdapter(lunarHourAdapter);
        hour.setCyclic(true);// 是否可循环滑动
    }

    /**
     * 加载公历日历
     *
     * @param curYear
     * @param curMonth
     */
    private void setGregorianDate(int curYear, int curMonth) {
        NumericLunarWheelAdapter lunarYearAdapter = new NumericLunarWheelAdapter(context, years);
        lunarYearAdapter.setLabel("年");
        year.setViewAdapter(lunarYearAdapter);
        year.setCyclic(true);// 是否可循环滑动


        NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(context, 1, 12, "%02d");
        numericWheelAdapter2.setLabel("月");
        month.setViewAdapter(numericWheelAdapter2);
        month.setCyclic(true);


        initDay(curYear, curMonth);
        day.setCyclic(true);

        NumericWheelAdapter hourAdapter = new NumericWheelAdapter(context, 0, 23, "%02d");
        hourAdapter.setLabel("时");
        hour.setViewAdapter(hourAdapter);
        hour.setCyclic(true);

    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(BaseWheelView wheel) {

        }

        @Override
        public void onScrollingFinished(BaseWheelView wheel) {
            int yearDate = year.getCurrentItem() + YEAR_START;// 年
            int monthDate = month.getCurrentItem() + 1;// 月
            int dayDate = day.getCurrentItem() + 1;//日
            int hourDate = hour.getCurrentItem();
            initDay(yearDate, monthDate);
            setDataMap(yearDate, monthDate, dayDate, hourDate);
            if (isLunar) {
                int month = monthDate;
                //当有闰月时   月份为当前月份+12 ， 如闰八月即为20月
                if (lunarMonths[monthDate - 1].startsWith("闰")) {
                    month = monthDate - 1 + 12;
                }
                lunarDate.setDate(yearDate, month, dayDate, hourDate);
                gregorianDate = ChineseCalendarTwo.sCalendarLundarToSolarToDate(yearDate, month, dayDate, hourDate);
            } else {
                gregorianDate.setDate(yearDate, monthDate, dayDate, hourDate);
                lunarDate = ChineseCalendarTwo.sCalendarSolarToLunarToDate(yearDate, monthDate, dayDate, hourDate);
            }
//            String date = getDataMap();
//            if (isLunar) {
//                lunarDate = date;
//                lastDate = ChineseCalendarTwo.sCalendarLundarToSolar(yearDate, monthDate, dayDate, hourDate);
//            } else {
//                lastDate = date;
//            }


        }
    };

//    /**
//     * 获取lastDate
//     */
//    private String getLastDate() {
//        int yearDate = year.getCurrentItem() + 1950;// 年
//        int monthDate = month.getCurrentItem() + 1;// 月
//        int dayDate = day.getCurrentItem() + 1;//日
//        int hourDate = hour.getCurrentItem();
//        setDataMap(yearDate, monthDate, dayDate, hourDate);
//        String date = getDataMap();
//        if (isLunar) {
////            lunarDate = date;
//            lastDate = ChineseCalendarTwo.sCalendarLundarToSolar(yearDate, monthDate, dayDate, hourDate);
//        } else {
//            lastDate = date;
//        }
//        return lastDate;
//    }

    /**
     */
    private void initDay(int arg1, int arg2) {

        if (isLunar) {
            //当前年份
            int currentYear = Integer.parseInt(years[year.getCurrentItem()]);
            int monthLength;
//        //是否有闰月
            int monthLeap = LunarUtil.getMonthLeapByYear(currentYear);
            if (monthLeap == 0) {
                monthLength = 12;
                lunarMonths = LunarUtil.lunarMonths;
            } else {
                monthLength = 13;
                lunarMonths = LunarUtil.getLunarMonthsNamesWithLeap(monthLeap);
            }
            months = new String[monthLength];
            for (int i = 0; i < monthLength; i++) {
                months[i] = (i + 1) + "";
            }
            NumericLunarWheelAdapter lunarMonthAdapter = new NumericLunarWheelAdapter(context, lunarMonths);
            lunarMonthAdapter.setLabel("月");
            month.setViewAdapter(lunarMonthAdapter);
            month.setCyclic(true);// 是否可循环滑动


            //当前月份
            int currentMonth = Integer.parseInt(months[month.getCurrentItem()]);
            int dayLength = LunarUtil.getSumOfDayInMonthForLunarByMonthSway(currentYear, currentMonth);
//            int dayLength = 28;
            lunarDays = new String[dayLength];
            days = new String[dayLength];
            for (int i = 0; i < dayLength; i++) {
                lunarDays[i] = LunarUtil.getLunarNameOfDay(i + 1);
                days[i] = i + "";
            }
            NumericLunarWheelAdapter lunarDayAdapter = new NumericLunarWheelAdapter(context, lunarDays);
            lunarDayAdapter.setLabel("日");
            day.setViewAdapter(lunarDayAdapter);

        } else {
            NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(arg1, arg2), "%02d");
            numericWheelAdapter.setLabel("日");
            day.setViewAdapter(numericWheelAdapter);
        }

    }

    /**
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    public DateDayDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

//    public DateDayDialog setMsg(String msg) {
//        showMsg = true;
//        return this;
//    }

    public DateDayDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public DateDayDialog setPositiveButton(String text, final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                boolean flag = false;
                if (dateDialogValue != null) {
//                    lastDate = getLastDate();
//                    initDate = lastDate;
//                    Log.e(tag, "确定  时  initDate = " + initDate);
                    flag = dateDialogValue.findDateDialogValue(gregorianDate.getDate());
                }
                if (flag)
                    dialog.dismiss();
            }
        });
        return this;
    }

    public DateDayDialog setNegativeButton(String text, final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    private void setLayout() {
        if (!showTitle) {
            txt_title.setText("日期");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

//        if (showMsg) {
//            txt_msg.setVisibility(View.VISIBLE);
//        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        txt_msg.removeAllViews();
        txt_msg.addView(getDataPick());
        dialog.show();
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(onKeyListener);
    }

    public void setCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    ;

    public interface DateDialogValue {
        boolean findDateDialogValue(String date);
    }

    public interface OnKeyDownListener {
        void onKeyListener();
    }
}
