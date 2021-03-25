package com.landleaf.homeauto.center.device.model.dto.protocol;

import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrBit;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrPrecision;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrSelect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 协议属性信息
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProtocolAttrInfoBO", description="设备属性信息缓存")
public class DeviceAttrInfoCacheBO {

    @ApiModelProperty(value = "属性主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "值类型")
    private Integer valueType;

    @ApiModelProperty(value = "协议属性具体值配置 valueType--值类型是1 枚举")
    private List<ProtocolAttrSelect> protocolAttrDetail;

    @ApiModelProperty(value = "协议属性二进制值配置 valueType--值类型是3 二进制")
    private List<ProtocolAttrBit> protocolAttrBitDTO;

    @ApiModelProperty(value = "协议属性具体值配置 valueType--值类型是2 数值")
    private ProtocolAttrPrecision protocolAttrPrecision;


}
