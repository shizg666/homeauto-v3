package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 产品通信故障属性值对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductErrorConnectAttrValueBO", description = "产品通信故障属性值对象")
public class ScreenProductErrorConnectAttrValueBO extends ScreenProductErrorAttrValueBaseBO {

    @ApiModelProperty(value = "正常值")
    private Integer normalVal;

    @ApiModelProperty(value = "异常值")
    private Integer unnormalVal;

}
