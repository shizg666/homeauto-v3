package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="ProductProtocolInfoBO", description="产品关联的协议信息")
public class ProductProtocolInfoBO {

    @ApiModelProperty(value = "协议id")
    private String protocolId;

    @ApiModelProperty(value = "协议code")
    private String protocolCode;

    @ApiModelProperty(value = "类别code")
    private String categoryCode;



}
