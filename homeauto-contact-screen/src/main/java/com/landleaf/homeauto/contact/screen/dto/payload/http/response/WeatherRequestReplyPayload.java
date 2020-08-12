package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import lombok.Data;

/**
 * 天气信息请求响应
 *
 * @author wenyilu
 */
@Data
public class WeatherRequestReplyPayload {

    /**
     * 日历(八月初四)
     */
    private String calender;

    private String cityName;

    private String cityUrl;
    /**
     * 冷热级别(少发)
     */
    private String coldLevel;
    /**
     * 日期(2019年09月02日)
     */
    private String date;
    /**
     * 穿衣指数(热)
     */
    private String dressLevel;
    /**
     * 湿度(39)
     */
    private String humidity;

    private String newPicUrl;
    /**
     * 图片地址(http://47.100.3.98:30002/images/new_ico/00晴.png)
     */
    private String picUrl;
    /**
     * pm25(29)
     */
    private String pm25;
    /**
     * 日期(2019.09.02)
     */
    private String singleCalender;
    /**
     * 运动指数(较适宜)
     */
    private String sportLevel;
    /**
     * 温度(31)
     */
    private String temp;
    /**
     * 更新时间(2019.09.02 14:00 发布)
     */
    private String updateTime;
    /**
     * 紫外线级别(很强)
     */
    private String uvLevel;
    /**
     * 天气(晴)
     */
    private String weatherStatus;
    /**
     * 星期(星期一)
     */
    private String week;
    /**
     * 风向(西南风)
     */
    private String windDirection;
    /**
     * 风力级别(2级)
     */
    private String windLevel;


}
