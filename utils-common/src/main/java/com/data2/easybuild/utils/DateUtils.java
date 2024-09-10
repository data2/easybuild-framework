package com.data2.easybuild.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
        extends org.apache.commons.lang3.time.DateUtils {
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyyMMddHHmmss"};

    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if ((pattern != null) && (pattern.length > 0)) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, new Object[]{"yyyy-MM-dd HH:mm:ss"});
    }

    public static String getTime() {
        return formatDate(new Date(), new Object[]{"HH:mm:ss"});
    }

    public static String getDateTime() {
        return formatDate(new Date(), new Object[]{"yyyy-MM-dd HH:mm:ss"});
    }

    public static String getYear() {
        return formatDate(new Date(), new Object[]{"yyyy"});
    }

    public static String getMonth() {
        return formatDate(new Date(), new Object[]{"MM"});
    }

    public static String getDay() {
        return formatDate(new Date(), new Object[]{"dd"});
    }

    public static String getWeek() {
        return formatDate(new Date(), new Object[]{"E"});
    }

    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
        }
        return null;
    }

    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 86400000L;
    }

    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 3600000L;
    }

    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 60000L;
    }

    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / 86400000L;
        long hour = timeMillis / 3600000L - day * 24L;
        long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
        long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
        return (day > 0L ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 86400000L;
    }

    public static String convertTimeToFormat(Date before) {
        long curTime = System.currentTimeMillis() / 1000L;
        long time = curTime - before.getTime();
        if ((time < 60L) && (time >= 0L)) {
            return "刚刚";
        }
        if ((time >= 60L) && (time < 3600L)) {
            return time / 60L + "分钟前";
        }
        if ((time >= 3600L) && (time < 86400L)) {
            return time / 3600L + "小时前";
        }
        if ((time >= 86400L) && (time < 2592000L)) {
            return time / 3600L / 24L + "天前";
        }
        if ((time >= 2592000L) && (time < 31104000L)) {
            return time / 3600L / 24L / 30L + "个月前";
        }
        if (time >= 31104000L) {
            return time / 3600L / 24L / 30L / 12L + "年前";
        }
        return "刚刚";
    }

    public static long convertStringToTime(String dateTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static String convertTimeToDateStr(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timeMillis));
    }

    public static void main(String[] args)
            throws ParseException {
    }
}

