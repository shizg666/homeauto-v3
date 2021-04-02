package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 产品数值故障属性值对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductErrorNumAttrValueBO", description = "产品数值故障属性值对象")
public class ScreenProductErrorNumAttrValueBO extends ScreenProductErrorAttrValueBaseBO {

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;

}
