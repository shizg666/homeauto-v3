package com.landleaf.homeauto.center.device.model.dto.protocol;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
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
@ApiModel(value="ProtocolAttrQryInfoDTO", description="协议属性查询")
public class ProtocolAttrQryInfoDTO extends BaseQry {

    @ApiModelProperty(value = "协议主键id")
    private String protocolId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;


    @ApiModelProperty(value = "属性类型")
    private Integer type;

    @ApiModelProperty(value = "品类")
    private String category;




}
