package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 小程序定时场景详情视图对象
 *
 * @author pilo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("小程序定时场景详情视图对象")
public class AppletsSceneTimingDetailVO {

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
    private List<String> repeatValue;

    @ApiModelProperty(value = "是否跳过节假日", required = true)
    private Integer skipHoliday;

    @ApiModelProperty("是否启用")
    private Integer enabled;

}
