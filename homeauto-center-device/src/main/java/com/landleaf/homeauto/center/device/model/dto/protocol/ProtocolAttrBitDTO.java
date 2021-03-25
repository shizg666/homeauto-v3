package com.landleaf.homeauto.center.device.model.dto.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 协议属性二进制值配置
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProtocolAttrBitDTO", description="协议属性二进制值配置")
public class ProtocolAttrBitDTO  {


    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "1 代表的描述")
    private String bit1;

    @ApiModelProperty(value = "0 代表的描述")
    private String bit0;

    @ApiModelProperty(value = "位")
    private Integer bitPos;

    @ApiModelProperty(value = "属性主键id")
    private String attrId;



}
