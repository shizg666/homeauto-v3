package com.landleaf.homeauto.center.device.model.smart.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 家庭定时场景业务对象
 *
 * @author Yujiumin
 * @version 2020/10/19
 */
@Data
@ApiModel("家庭定时场景视图对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilySceneTimingVO {

    @ApiModelProperty("定时场景ID")
    private String timingSceneId;

    @ApiModelProperty("执行场景ID")
    private String executeSceneId;

    @ApiModelProperty("执行场景名称")
    private String executeSceneName;

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("执行时间")
    private String executeTime;

    @ApiModelProperty("重复类型")
    private Integer repeatType;

    @ApiModelProperty("重复类型的值")
    private String repeatTypeValue;

    @ApiModelProperty("是否跳过法定节假日(1|0 是|否)")
    private Integer skipHoliday;

    @ApiModelProperty("是否启用")
    private Integer enabled;

    // 以下字段用于兼容旧接口

    @Deprecated
    @ApiModelProperty("定时场景ID")
    private String timingId;

    @Deprecated
    @ApiModelProperty("场景名称")
    private String sceneName;

    @Deprecated
    @ApiModelProperty("重复时间")
    private String workday;

    @Deprecated
    @ApiModelProperty("重复类型的值")
    private String repeatValue;

    @Deprecated
    @ApiModelProperty("场景列表")
    private List<FamilySceneVO> scenes;

}
