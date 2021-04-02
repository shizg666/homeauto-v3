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
public class TimingSceneDTO {

    @ApiModelProperty(value = "定时场景ID (添加时不需要,更新时需要)")
    private String timingId;

    @ApiModelProperty(value = "家庭ID", required = true)
    private String familyId;

    @ApiModelProperty(value = "场景ID", required = true)
    private String sceneId;

    @ApiModelProperty(value = "执行时间", required = true)
    private String executeTime;

    @ApiModelProperty(value = "重复类型 (1:不重复, 2:按周重复, 3:按日期重复)", required = true)
    private Integer repeatType;

    @ApiModelProperty(value = "重复类型的值")
    private String repeatValue;

    @ApiModelProperty(value = "是否跳过节假日", required = true)
    private Integer skipHoliday;

    @ApiModelProperty("是否启用")
    private Integer enabled;

}
