package com.landleaf.homeauto.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName LocalDateTimeUtils
 * @Description: JDK1.8LocalDateTime工具类
 * 之前的建议使用commons-lang3 下的时间工具类DateUtils/DateFormatUtils
 * @Author shizg
 * @Date 2020/6/1
 * @Version V1.0
 **/
public final class LocalDateTimeUtil {


    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY__MM__DD = "yyyy/MM/dd";

    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY__MM = "yyyy/MM";

    private LocalDateTimeUtil() {

    }
    /**
     * 其他方法见JDK
     */
    // 根据年月日构建
    // LocalDateTime.of()
    // 比较日期先后
    // LocalDateTime.now().isBefore()
    // LocalDateTime.now().isAfter()


    /**
     * 获取当前时间的LocalDateTime对象
     *
     * @return
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前的时间戳
     */
    public static long nowTimeStamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * Date转换为LocalDateTime.
     *
     * @param date
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date.
     *
     * @param time
     * @return java.util.Date
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取指定日期的毫秒.
     *
     * @param time
     * @return java.lang.Long
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒.
     *
     * @param time
     * @return java.lang.Long
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 字符串转换为localDateTime
     *
     * @param dataStr
     * @param pattern
     * @return
     */
    public static LocalDateTime parseStr(String dataStr, String pattern) {
        if (StringUtil.isBlank(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dataStr, dateTimeFormatter);
    }

    /**
     * 获取指定时间的指定格式字符串.
     *
     * @param time
     * @param pattern
     * @return java.lang.String
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式字符串.
     *
     * @param pattern
     * @return java.lang.String
     */
    public static String nowformat(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*.
     *
     * @param time
     * @param number
     * @param field
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*.
     *
     * @param time
     * @param number
     * @param field
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*.
     *
     * @param startTime
     * @param endTime
     * @param field
     * @return long
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取一天的开始时间，2017,7,22 00:00.
     *
     * @param time
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
//        return time.withHour(0)
//                .withMinute(0)
//                .withSecond(0)
//                .withNano(0);
        return time.with(LocalTime.MIN);
    }


    /**
     * 获取一天的结束时间，2017,7,22 23:59:59.999999999.
     *
     * @param time
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.with(LocalTime.MAX);
//        return time.withHour(23)
//                .withMinute(59)
//                .withSecond(59)
//                .withNano(999999999);
    }

    /**
     * 获取当月的天数
     *
     * @return
     */
    public static int getCurrentMonthDays() {
        YearMonth month = YearMonth.now();
        return month.lengthOfMonth();
    }

    /**
     * 获取当前月的第一天
     *
     * @return
     */
    public static LocalDateTime getMonthOfFirstDay() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static LocalDateTime getMonthOfLastDay() {
        return LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * 获取某月的第一天
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getMonthOfFirstDay(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取某月的最后一天
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getMonthEnd(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * 获取上月的结束时间
     *
     * @return
     */
    public static LocalDateTime getLastMonth() {
//        Calendar calendar = Calendar.getInstance(LocalDate.now());
//        calendar.setTime(LocalDate.now());
        return null;
    }

    /**
     * 获取当日的开始时间
     *
     * @return
     */
    public static LocalDateTime getNowStart() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 获取当日的结束时间
     *
     * @return
     */
    public static LocalDateTime getNowEnd() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }


    /**
     * 根据表达式 str转date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date str2DatePattern(String dateStr, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        if (YYYY_MM_DD_HH_MM_SS.equals(pattern)) {
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr, df);
            return localDateTime2Date(localDateTime);
        }
        if (YYYY_MM_DD.equals(pattern)) {
            LocalDate localDateTime = LocalDate.parse(dateStr, df);
            return localDate2Date(localDateTime);
        }
        if (YYYY_MM.equals(pattern)) {
            dateStr = dateStr + "-01";
            df = DateTimeFormatter.ofPattern(YYYY_MM_DD);
            LocalDate localDateTime = LocalDate.parse(dateStr, df);
            return localDate2Date(localDateTime);
        }
        if (HH_MM_SS.equals(pattern)) {
            LocalTime localDateTime = LocalTime.parse(dateStr, df);
            return localTime2Date(localDateTime);
        }
        return null;
    }

    /**
     * 根据表达式 date转str
     *
     * @param date
     * @return
     */
    public static String date2StrPattern(Date date, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(date2LocalDateTime(date));
    }

    /**
     * date转str
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        return df.format(date2LocalDateTime(date));
    }

    /**
     * str转date
     *
     * @param dateStr
     * @return
     */
    public static Date str2Date(String dateStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, df);
        return localDateTime2Date(localDateTime);
    }

    /**
     * date转localDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * localDateTime转date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }


    /**
     * date转localDate
     *
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        return date2LocalDateTime(date).toLocalDate();
    }

    /**
     * localDate转date
     *
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        return Date.from(instant);
    }


    /**
     * date转localTime
     *
     * @param date
     * @return
     */
    public static LocalTime date2LocalTime(Date date) {
        return date2LocalDateTime(date).toLocalTime();
    }

    /**
     * localTime转date
     *
     * @param localTime
     * @return
     */
    public static Date localTime2Date(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }


    /**
     * 获取Calendar对象
     *
     * @param date
     * @return
     */
    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取日期的年份
     *
     * @param date
     * @return
     */
    public static int year(Date date) {
        return date2LocalDateTime(date).getYear();
    }


    /**
     * 获取日期的月份
     *
     * @param date
     * @return
     */
    public static int month(Date date) {
        return date2LocalDateTime(date).getMonthValue();
    }

    /**
     * 获取日
     *
     * @param date
     * @return
     */
    public static int day(Date date) {
        return date2LocalDateTime(date).getDayOfMonth();
    }


    /**
     * 获取年月字符串
     *
     * @param year
     * @param month
     * @return
     */
    public static String getYearAndMonth(int year, int month) {
        return year + "-" + (month > 9 ? month : "0" + month);
    }

    /**
     * 获取年月字符串
     *
     * @param date
     * @return
     */
    public static String getYearAndMonth(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        return year + "-" + (month > 9 ? month : "0" + month);
    }


    public static Date offsetDate(Date date, long amountToAdd, TemporalUnit unit) {
        return localDateTime2Date(date2LocalDateTime(date).plus(amountToAdd, unit));
    }


    /**
     * 前一小时
     *
     * @param date
     * @return
     */
    public static Date prevHour(Date date) {
        return offsetDate(date, -1, ChronoUnit.HOURS);
    }

    /**
     * 后一小时
     *
     * @param date
     * @return
     */
    public static Date nextHour(Date date) {
        return offsetDate(date, 1, ChronoUnit.HOURS);
    }


    /**
     * 前一天
     *
     * @param date
     * @return
     */
    public static Date prevDay(Date date) {
        return offsetDate(date, -1, ChronoUnit.DAYS);
    }

    /**
     * 后一天
     *
     * @param date
     * @return
     */
    public static Date nextDay(Date date) {
        return offsetDate(date, 1, ChronoUnit.DAYS);
    }

    /**
     * 后几天
     *
     * @param date
     * @return
     */
    public static Date nextSomeDay(Date date, int days) {
        return offsetDate(date, days, ChronoUnit.DAYS);
    }


    /**
     * 前一月
     *
     * @param date
     * @return
     */
    public static Date prevMonth(Date date) {
        return offsetDate(date, -1, ChronoUnit.MONTHS);
    }

    /**
     * 后一月
     *
     * @param date
     * @return
     */
    public static Date nextMonth(Date date) {
        return offsetDate(date, 1, ChronoUnit.MONTHS);
    }


    /**
     * 前一月
     *
     * @param date
     * @return
     */
    public static Date prevYear(Date date) {
        return offsetDate(date, -1, ChronoUnit.YEARS);
    }

    /**
     * 后一月
     *
     * @param date
     * @return
     */
    public static Date nextYear(Date date) {
        return offsetDate(date, 1, ChronoUnit.YEARS);
    }


    /**
     * 两日期之间的不同维度差值
     *
     * @param sDate      之前的时间
     * @param bDate      后的时间
     * @param chronoUnit
     * @return
     */
    public static long diff(Date sDate, Date bDate, ChronoUnit chronoUnit) {
        switch (chronoUnit) {
            case SECONDS:
                return ChronoUnit.SECONDS.between(date2LocalDateTime(sDate), date2LocalDateTime(bDate));
            case MINUTES:
                return ChronoUnit.MINUTES.between(date2LocalDateTime(sDate), date2LocalDateTime(bDate));
            case HOURS:
                return ChronoUnit.HOURS.between(date2LocalDateTime(sDate), date2LocalDateTime(bDate));
            case DAYS:
                return ChronoUnit.DAYS.between(date2LocalDate(sDate), date2LocalDate(bDate));
            case MONTHS:
                return ChronoUnit.MONTHS.between(date2LocalDate(sDate), date2LocalDate(bDate));
            case YEARS:
                return ChronoUnit.YEARS.between(date2LocalDate(sDate), date2LocalDate(bDate));

            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = getMonthOfFirstDay(LocalDateTime.now());
        LocalDateTime localDateTime2 = getMonthOfFirstDay(LocalDateTime.now());
        LocalDateTime localDateTime3 = parseStr("2020-02-29 23:59:59", "");
        LocalDateTime localDateTime6 = minu(localDateTime3, 1, ChronoUnit.MONTHS);
        LocalDateTime localDateTime4 = parseStr("2020-06-02 10:30:00", "");
        long d = betweenTwoTime(localDateTime3, localDateTime4, ChronoUnit.DAYS);
        System.out.println("args = [" + localDateTime3 + "]");
        System.out.println("args = [" + localDateTime6 + "]");
        System.out.println("args = [" + localDateTime2.getHour() + localDateTime2.getMinute() + localDateTime2.getSecond() + localDateTime2.getNano() + "]");


    }
}
