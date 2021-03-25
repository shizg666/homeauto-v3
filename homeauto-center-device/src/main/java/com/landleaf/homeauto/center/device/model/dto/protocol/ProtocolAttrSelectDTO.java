package com.landleaf.homeauto.center.device.model.dto.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 协议属性具体值配置
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProtocolAttrSelectDTO", description="协议属性具体值配置")
public class ProtocolAttrSelectDTO {


    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String value;

    @ApiModelProperty(value = "属性主键id")
    private String attrId;


}
