package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Yujiumin
 * @version 2020/8/20
 */
@Data
@NoArgsConstructor
@ApiModel("家庭定时场景数据传输对象")
public class FamilySceneTimingDTO {

    @ApiModelProperty(value = "定时场景ID", notes = "添加时不需要,更新时需要")
    private String timingId;

    @ApiModelProperty("场景ID")
    private String sceneId;

    @ApiModelProperty("执行时间")
    private String executeTime;

    @ApiModelProperty(value = "重复类型", notes = "1:不重复, 2:按周重复, 3:按日期重复")
    private Integer type;

    @ApiModelProperty(value = "星期", notes = "当type为2时必传")
    private String weekday;

    @ApiModelProperty(value = "开始日期", notes = "当type为3时必传")
    private String startDate;

    @ApiModelProperty(value = "结束日期", notes = "当type为3时必传")
    private String endDate;

    @ApiModelProperty("是否跳过节假日")
    private Integer skipHoliday;

    @ApiModelProperty("是否启用")
    private Integer enabled;

}
