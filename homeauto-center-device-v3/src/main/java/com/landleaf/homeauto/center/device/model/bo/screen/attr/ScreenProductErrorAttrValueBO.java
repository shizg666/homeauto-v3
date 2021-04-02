package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 产品故障属性值对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductErrorAttrValueBO", description = "产品故障属性值对象")
public class ScreenProductErrorAttrValueBO extends ScreenProductAttrValueBaseBO {
    @ApiModelProperty(value = "故障类型 1 故障码 2通信 3数值")
    private Integer type;
    @ApiModelProperty(value = "值类别为故障码时，值域对象")
    private List<ScreenProductErrorCodeAttrValueBO> codeAttrValue;
    @ApiModelProperty(value = "值类别为通信时，值域对象")
    private ScreenProductErrorConnectAttrValueBO connectAttrValue;
    @ApiModelProperty(value = "值类别为数值时，值域对象")
    private ScreenProductErrorNumAttrValueBO numAttrValue;
}
