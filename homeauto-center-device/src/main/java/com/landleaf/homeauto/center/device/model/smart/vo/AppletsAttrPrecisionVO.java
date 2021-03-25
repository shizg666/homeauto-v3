package com.landleaf.homeauto.center.device.model.smart.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 属性为精度類型时，展示精度控制屬性
 * </p>
 */
@Data
@ApiModel(value="AttrPrecision对象", description="属性为精度類型时，展示精度控制屬性")
public class AppletsAttrPrecisionVO{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "计算系数")
    private String calculationFactor;

    @ApiModelProperty(value = "精度")
    private Integer precision;

    @ApiModelProperty(value = "步长")
    private String step;

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;


}
