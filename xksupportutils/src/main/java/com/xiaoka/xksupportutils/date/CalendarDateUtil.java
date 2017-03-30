package com.xiaoka.xksupportutils.date;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shenmengchao on 16/5/3.
 * 日期转换工具
 */
public class CalendarDateUtil {

    public static final String TAG = "XKSupportDateUtil";

    /**
     * 日期格式化的格式 "yyyy-MM-dd" 年-月-日
     */
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 日期格式化的格式 "yyyy-MM-dd HH:mm:ss" 年-月-日 小时：分：秒
     */
    public final static String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据时间戳，返回 "小时：分" 时间格式
     * @param time
     * @return
     */
    public static String getHourAndMinuteStr(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(time));
    }

    /**
     * 根据时间戳，返回自定义的时间格式，时间戳是以毫秒为单位
     * @param time
     * @param format
     * @return
     */
    public static String getDateStrFromMillions(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    /**
     * 根据时间戳，返回自定义的时间格式，时间戳是以秒为单位
     * @param time
     * @param format
     * @return
     */
    public static String getDateStrFromSecond(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time * 1000));
    }

    /**
     * 根据自定义的时间格式，返回时间戳
     * @param dateStr
     * @param format
     * @return
     */
    public static long getLong(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "时间格式转换错误，请确认你的时间格式！");
            return 0;
        }
        return date.getTime();
    }

    /**
     * 传入两个时间戳，将前时间戳的小时和分，设置为后时间戳的小时和分，并将修改后的时间戳返回
     * @param timeOriginal
     * @param timeHourAndMinute
     * @return
     */
    public static long setHourAndMinute(long timeOriginal, long timeHourAndMinute) {
        Calendar calendarHourAndMinute = Calendar.getInstance();
        calendarHourAndMinute.setTimeInMillis(timeHourAndMinute);
        Calendar calendarOriginal = Calendar.getInstance();
        calendarOriginal.setTimeInMillis(timeOriginal);
        calendarOriginal.set(calendarOriginal.get(Calendar.YEAR), calendarOriginal.get(Calendar.MONTH), calendarOriginal.get(Calendar.DAY_OF_MONTH),
                calendarHourAndMinute.get(Calendar.HOUR_OF_DAY), calendarHourAndMinute.get(Calendar.MINUTE), 0);

        return calendarOriginal.getTimeInMillis();
    }

    /**
     * @param year
     * @param month 从0开始计算
     * @param day
     * @return
     */
    public static long getLong(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    /**
     * 传入 年 月 日 小时 分 秒 返回由此生成的时间戳
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static long getLong(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTimeInMillis();
    }

    /**
     * 传入小时，分 返回由此生成的时间戳（其他要素不考虑）
     * @param hour
     * @param minute
     * @return
     */
    public static long getLong(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 1, 1, hour, minute, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回当前时间的时间戳
     * @return
     */
    public static long getCurrentLong() {
        return (new Date()).getTime();
    }

    /**
     * 返回当前时间的时间戳，并且将小时，分，秒及以后的时间，都置为0
     * @return
     */
    public static long getCurrentDateLong() {
        long currentLong = getCurrentLong();
        long currentDateLong = getLong(getYear(currentLong), getMonth(currentLong), getDay(currentLong), 0, 0, 0);
        return currentDateLong / 1000 * 1000;
    }

    /**
     * 根据传入的时间戳，返回年分
     * @param timeLong
     * @return
     */
    public static int getYear(long timeLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 根据传入的时间戳，返回月份
     * @param timeLong
     * @return
     */
    public static int getMonth(long timeLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 根据传入的时间戳，返回天
     * @param timeLong
     * @return
     */
    public static int getDay(long timeLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据传入的时间戳，返回小时
     * @param timeLong
     * @return
     */
    public static int getHour(long timeLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 根据传入的时间戳，返回分钟
     * @param timeLong
     * @return
     */
    public static int getMinute(long timeLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeLong);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间的年份
     * @return
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     * @return
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    public static String getCurrentMonthString() {
        return Calendar.getInstance().get(Calendar.MONTH)+1+"";
    }
    
    /**
     * 获取当前时间的日期
     * @return
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据传入时间戳，返回星期
     * @param timeLong
     * @return
     */
    public static String getWeek(long timeLong){
        String time = getDateStrFromMillions(timeLong, FORMAT_DATE);
        return getWeek(time);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
//  String pTime = "2012-03-12";
    public static String getWeek(String pTime) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }
        return Week;
    }

    /**
     * 日期格式字符串转换成时间戳
     * 使用 {@link #getLong(String, String)} 代替
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    @Deprecated
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串格式的时间形式标准化
     * 使用 {@link #getLong(String, String)} 代替
     * @author changping
     *
     * created at 2016/3/23 17:42
     */
    @Deprecated
    public static String StrDate2StrDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date _date;
        try {
            _date = sdf.parse(dateStr);
            String convertDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(_date);
            return convertDateStr;
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return "";
    }

    /**
     * 比较两个字符串时间的大小
     *
     * @author changping
     *
     * created at 2016/3/23 17:48
     */
    public static long compareStrDate(String startDate, String endDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d1.getTime() - d2.getTime();
            return diff;
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return 0;
    }

    /**
     * 已废弃
     * 使用 {@link #getDateStrFromMillions(long, String)} 代替
     * long 类型可以使用 {@link #getCurrentLong()} 获取
     * @param date
     * @param format
     * @return
     */
    @Deprecated
    public static String DateConvertString(Date date, String format) {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String dateStr=sdf.format(date);
        return dateStr;
    }
}
