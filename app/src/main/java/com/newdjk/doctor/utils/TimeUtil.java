package com.newdjk.doctor.utils;


import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 时间转换工具
 */
public class TimeUtil {


    private TimeUtil() {
    }

    /**
     * 时间转化为显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getTimeStr(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp * 1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        if (calendar.before(inputTime)) {
            //今天23:59在输入时间之前，解决一些时间误差，把当天时间显示到这里
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + MyApplication.getContext().getResources().getString(R.string.time_year) + "MM" + MyApplication.getContext().getResources().getString(R.string.time_month) + "dd" + MyApplication.getContext().getResources().getString(R.string.time_day));
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            return MyApplication.getContext().getResources().getString(R.string.time_yesterday);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M" + MyApplication.getContext().getResources().getString(R.string.time_month) + "d" + MyApplication.getContext().getResources().getString(R.string.time_day));
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + MyApplication.getContext().getResources().getString(R.string.time_year) + "MM" + MyApplication.getContext().getResources().getString(R.string.time_month) + "dd" + MyApplication.getContext().getResources().getString(R.string.time_day));
                return sdf.format(currenTimeZone);

            }

        }

    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getChatTimeStr(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp * 1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (!calendar.after(inputTime)) {
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + MyApplication.getContext().getResources().getString(R.string.time_year) + "MM" + MyApplication.getContext().getResources().getString(R.string.time_month) + "dd" + MyApplication.getContext().getResources().getString(R.string.time_day));
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return MyApplication.getContext().getResources().getString(R.string.time_yesterday) + " " + sdf.format(currenTimeZone);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M" + MyApplication.getContext().getResources().getString(R.string.time_month) + "d" + MyApplication.getContext().getResources().getString(R.string.time_day) + " HH:mm");
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + MyApplication.getContext().getResources().getString(R.string.time_year) + "MM" + MyApplication.getContext().getResources().getString(R.string.time_month) + "dd" + MyApplication.getContext().getResources().getString(R.string.time_day) + " HH:mm");
                return sdf.format(currenTimeZone);
            }

        }

    }

    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }

        return sb.toString();
    }


    public static String getCurrentTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);
    }

    public static String getCountTime(long time) {
        String hh = new DecimalFormat("00").format(time / 3600);
        String mm = new DecimalFormat("00").format(time % 3600 / 60);
        String ss = new DecimalFormat("00").format(time % 60);
        String timeFormat = new String(hh + ":" + mm + ":" + ss);
        return timeFormat;
    }

    //字符串转换成时间戳
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    //判读在多长时间内
    public static String showTime(String dateString, String format) {

        long ctimelong = getStringToDate(dateString, format);

        String r = "";
        if (dateString == null) return r;
        if (format == null) format = "MM-dd HH:mm";

        long nowtimelong = System.currentTimeMillis();

        long result = Math.abs(nowtimelong - ctimelong);

        if (result < 60000) {// 一分钟内
            long seconds = result / 1000;
            if (seconds == 0) {
                r = "刚刚";
            } else {
                r = seconds + "秒前";
            }
        } else if (result >= 60000 && result < 3600000) {// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        }
        //      else if (result >= 3600000 && result < 86400000){// 一天内
//            long seconds = result / 3600000;
//            r = seconds + "小时前";
//        }else if (result >= 86400000 && result < 1702967296){// 三十天内
//            long seconds = result / 86400000;
//            r = seconds + "天前";
        //      }
        else {// 日期格式
            format = "yyyy-MM-dd HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(format);
            r = df.format(ctimelong).toString();
        }
        return r;
    }


    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    /**
     * 获取时
     *
     * @return
     */
    public static int getHour() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.HOUR);
    }

    /**
     * 获取分
     *
     * @return
     */
    public static int getMinute() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    public static String getDate() {
        Date d = new Date();
        if (d.getHours() < 11) {
            return ",早上好!";
        } else if (d.getHours() < 13) {
            return ",中午好!";
        } else if (d.getHours() < 18) {
            return ",下午好!";
        } else if (d.getHours() < 24) {
            return ",晚上好!";
        }
        return "";
    }

    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
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

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static String getweek(int weekday) {
        String week = "";
        switch (weekday) {
            case 1:
                week = "(星期一)";
                break;
            case 2:
                week = "(星期二)";
                break;
            case 3:
                week = "(星期三)";
                break;
            case 4:
                week = "(星期四)";
                break;
            case 5:
                week = "(星期五)";
                break;
            case 6:
                week = "(星期六)";
                break;
            case 7:
                week = "(星期日)";
                break;

        }
        return week;
    }
}
