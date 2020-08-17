package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.center.device.model.po.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 场景定时配置表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilySceneTiming对象", description="场景定时配置表")
public class FamilySceneTimingDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "智能场景id")
    private String sceneId;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime executeTime;

    @ApiModelProperty(value = "设置类型：1不重复 2自定义周 3自定义日历")
    private Integer type;

    @ApiModelProperty(value = "启用标志")
    private Integer enableFlag;

    @ApiModelProperty(value = "是否跳过法定节假日")
    private Integer holidaySkipFlag;

    @ApiModelProperty(value = "周配置")
    private String weekday;

    @ApiModelProperty(value = "开始日期")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束日期")
    private LocalDate endDate;


}
