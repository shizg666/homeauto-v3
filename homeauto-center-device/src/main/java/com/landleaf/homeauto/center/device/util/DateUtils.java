package com.landleaf.homeauto.center.device.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * @author Yujiumin
 * @version 2020/8/25
 */
public class DateUtils {

    /**
     * 将日期转为时间字符串
     *
     * @param localDate 日期对象
     * @param pattern   格式
     * @return 时间字符串
     */
    public static String toTimeString(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 将时间转为时间字符串
     *
     * @param localTime 时间对象
     * @param pattern   格式
     * @return 时间字符串
     */
    public static String toTimeString(LocalTime localTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        return localTime.format(dateTimeFormatter);
    }

    /**
     * 将时间字符串转为时间对象
     *
     * @param timeString 时间字符串
     * @param pattern    格式
     * @return 时间字符串
     */
    public static LocalTime parseLocalTime(String timeString, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(timeString);
        return LocalTime.from(temporalAccessor);
    }

    /**
     * 将时间字符串转为时间对象
     *
     * @param dateString 时间字符串
     * @param pattern    格式
     * @return 时间字符串
     */
    public static LocalDate parseLocalDate(String dateString, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(dateString);
        return LocalDate.from(temporalAccessor);
    }

}
