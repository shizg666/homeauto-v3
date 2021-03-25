package com.landleaf.homeauto.center.device.model.dto.protocol;

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
@ApiModel(value="ProtocolAttrInfoDTO", description="协议属性信息")
public class ProtocolAttrInfoDTO {

    @ApiModelProperty(value = "协议主键id （修改必传）")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;

    @ApiModelProperty(value = "操作权限 多个以/分隔")
    private String operateAcl;

    @ApiModelProperty(value = "属性类型")
    private Integer type;

    @ApiModelProperty(value = "值类型")
    private Integer valueType;

    @ApiModelProperty(value = "app是否读写")
    private Integer appFlag;

    @ApiModelProperty(value = "协议主键id")
    private String protocolId;

    @ApiModelProperty(value = "品类")
    private Integer category;

    @ApiModelProperty(value = "协议属性具体值配置 valueType--值类型是1 枚举")
    private List<ProtocolAttrSelectDTO> protocolAttrDetail;

    @ApiModelProperty(value = "协议属性二进制值配置 valueType--值类型是3 二进制")
    private List<ProtocolAttrBitDTO> protocolAttrBitDTO;

    @ApiModelProperty(value = "协议属性具体值配置 valueType--值类型是2 数值")
    private ProtocolAttrPrecisionDTO protocolAttrPrecision;


}
