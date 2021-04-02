package com.landleaf.homeauto.center.device.model.smart.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 家庭定时场景业务对象
 *
 * @author Yujiumin
 * @version 2020/10/19
 */
@Data
@ApiModel("家庭定时场景业务对象")
public class FamilySceneTimingBO {

    @ApiModelProperty("场景定时记录ID")
    private String timingId;

    @ApiModelProperty("执行场景ID")
    private String executeSceneId;

    @ApiModelProperty("执行场景名称")
    private String executeSceneName;

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("执行时间")
    private LocalTime executeTime;

    @ApiModelProperty("重复类型")
    private Integer repeatType;

    @ApiModelProperty("重复类型的值")
    private String repeatTypeValue;

    @ApiModelProperty("自定义周配置")
    private String weekday;

    @ApiModelProperty("开始日期")
    private LocalDate startDate;

    @ApiModelProperty("结束日期")
    private LocalDate endDate;

    @ApiModelProperty("是否跳过法定节假日(1|0 是|否)")
    private Integer skipHoliday;

    @ApiModelProperty("是否启用")
    private Integer enabled;
}
