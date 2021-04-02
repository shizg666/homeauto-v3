package com.landleaf.homeauto.center.device.util;

import com.landleaf.homeauto.common.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.LinkedList;
import java.util.List;
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

    /**
     * 把周换为数字
     *
     * @param weeks 周列表
     * @return 转换后的数字字符串
     */
    public static List<String> parseWeek(String... weeks) {
        List<String> weekList = new LinkedList<>();
        for (String week : weeks) {
            switch (week) {
                case "周一":
                    weekList.add("1");
                    break;
                case "周二":
                    weekList.add("2");
                    break;
                case "周三":
                    weekList.add("3");
                    break;
                case "周四":
                    weekList.add("4");
                    break;
                case "周五":
                    weekList.add("5");
                    break;
                case "周六":
                    weekList.add("6");
                    break;
                case "周日":
                    weekList.add("7");
                    break;
                default:
                    throw new BusinessException("星期传入错误");
            }
        }
        return weekList;
    }

}
