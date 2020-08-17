package com.landleaf.homeauto.center.device.model.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/8/17
 */
public enum FamilySceneTimingRepeatTypeEnum {

    /**
     * 不重复
     */
    NONE(1, "不重复") {
        @Override
        public String handleWorkDay(String timeString) {
            return "不重复";
        }

    },

    /**
     * 按星期重复
     */
    WEEK(2, "按周重复") {
        @Override
        public String handleWorkDay(String timeString) {
            // 用户选择的时间
            List<String> weekList = Arrays.asList(timeString.split(","));
            boolean isWeekday = weekList.contains("1") && weekList.contains("2") && weekList.contains("3") && weekList.contains("4") && weekList.contains("5");
            boolean isWeekend = weekList.contains("6") && weekList.contains("7");
            if (Objects.equals(weekList.size(), WEEK_DAYS_COUNT)) {
                // 如果是七天,则每天都重复
                return "每天";
            } else {
                if (Objects.equals(weekList.size(), WEEKDAY_COUNT) && isWeekday) {
                    // 如果是五天,并且包括周一到周五
                    return "工作日";
                } else if (Objects.equals(weekList.size(), WEEKEND_COUNT) && isWeekend) {
                    // 如果是两天,并且包括周六和周日
                    return "周末";
                } else {
                    // 显示所有的星期
                    return replaceWeek(weekList.toArray(new String[]{}));
                }
            }
        }
    },

    /**
     * 按日历重复
     */
    CALENDAR(3, "按日历重复") {
        @Override
        public String handleWorkDay(String timeString) {
            String[] timeArray = timeString.split(",");
            String startDate = timeArray[0];
            String endDate = timeArray[1];
            return startDate + "-" + endDate;
        }
    };

    protected Integer type;

    protected String name;

    private static final int WEEK_DAYS_COUNT = 7;

    private static final int WEEKDAY_COUNT = 5;

    private static final int WEEKEND_COUNT = 2;

    FamilySceneTimingRepeatTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 通过类型获取对应的枚举对象
     *
     * @param type 重复类型
     * @return 重复对象
     */
    public static FamilySceneTimingRepeatTypeEnum getByType(Integer type) {
        switch (type) {
            case 2:
                return WEEK;
            case 3:
                return CALENDAR;
            default:
                return NONE;
        }
    }

    public abstract String handleWorkDay(String timeString);

    public String replaceWeek(String... weeks) {
        StringBuilder builder = new StringBuilder();
        for (String week : weeks) {
            switch (week) {
                case "1":
                    builder.append("周一").append(",");
                    break;
                case "2":
                    builder.append("周二").append(",");
                    break;
                case "3":
                    builder.append("周三").append(",");
                    break;
                case "4":
                    builder.append("周四").append(",");
                    break;
                case "5":
                    builder.append("周五").append(",");
                    break;
                case "6":
                    builder.append("周六").append(",");
                    break;
                case "7":
                    builder.append("周日").append(",");
                    break;
                default:
                    builder.append("未知星期:").append(week).append(",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
