package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 定时场景信息
 **/
@Data
public class ContactScreenFamilyTimingScene {


    private String timingId;

    private String sceneName;

    private String sceneId;

    private String executeTime;

    private Integer type;

    private Integer enabled;

    private Integer skipHoliday;

    private String weekday;

    private String startDate;

    private String endDate;

}
