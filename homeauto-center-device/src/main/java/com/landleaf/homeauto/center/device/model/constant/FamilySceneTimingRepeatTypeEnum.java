package com.landleaf.homeauto.center.device.model.constant;

import java.util.Arrays;
import java.util.List;

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
            // 工作日集合
            List<String> weekdayList = Arrays.asList("1", "2", "3", "4", "5");
            // 周末集合
            List<String> weekendList = Arrays.asList("6", "7");
            // todo: 按周重复的逻辑
            return "逻辑待完成...";
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
