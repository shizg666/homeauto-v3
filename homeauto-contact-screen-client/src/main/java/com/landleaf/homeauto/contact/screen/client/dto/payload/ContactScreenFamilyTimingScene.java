package com.landleaf.homeauto.contact.screen.client.dto.payload;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 定时场景信息
 **/
@Data
public class ContactScreenFamilyTimingScene {


    /**
     * 配置Id
     */
    private String timingId;

    /**
     * 场景id
     */
    private String sceneId;
    /**
     * 场景名称
     */
    private String sceneName;

    /**
     * 开始时间 格式HH:mm
     */
    private String executeTime;

    /**
     * 设置类型：1不重复 2自定义周 3自定义日历
     */
    private Integer type;

    /**
     * 启用标志
     */
    private Integer enabled;

    /**
     * 是否跳过法定节假日
     */
    private Integer skipHoliday;

    /**
     * 周配置
     */
    private String weekday;

    /**
     * 开始日期  yyyy.MM.dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy.MM.dd
     */
    private String endDate;

}
