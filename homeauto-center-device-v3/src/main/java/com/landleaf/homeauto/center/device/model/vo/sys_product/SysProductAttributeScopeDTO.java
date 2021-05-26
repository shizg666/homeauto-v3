package com.landleaf.homeauto.center.device.model.vo.sys_product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品属性精度范围表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysProductAttributeScopeDTO", description="SysProductAttributeScopeDTO")
public class SysProductAttributeScopeDTO {


    private static final long serialVersionUID = -1435592564049441356L;

    @ApiModelProperty(value = "精度 ")
    private Integer precision;

    @ApiModelProperty(value = "属性范围最大值 ")
    private String max;

    @ApiModelProperty(value = "属性范围最小值")
    private String min;

    @ApiModelProperty(value = "属性范围步幅")
    private String step;
}
