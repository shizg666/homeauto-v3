package com.landleaf.homeauto.center.device.model.bo.screen;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 定时场景业务对象
 *
 * @author Yujiumin
 * @version 2020/8/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenFamilySceneTimingBO {

    private Long timingId;

    private Long sceneId;

    private String sceneName;

    private LocalTime executeTime;

    private Integer type;

    private Integer enabled;

    private Integer skipHoliday;

    private String weekday;

    private LocalDate startDate;

    private LocalDate endDate;


}
