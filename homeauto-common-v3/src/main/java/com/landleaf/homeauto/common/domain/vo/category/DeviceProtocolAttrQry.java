package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="DeviceProtocolAttrQry", description="设备协议属性查询对象")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceProtocolAttrQry {

    @ApiModelProperty(value = "协议id")
    private String protocolId;

    @ApiModelProperty(value = "协议code")
    private String protocolCode;

    @ApiModelProperty(value = "类别code")
    private String categoryCode;

    @ApiModelProperty(value = "控制区域")
    private String controlArea;

    @ApiModelProperty(value = "设别编号")
    private String sn;



}
