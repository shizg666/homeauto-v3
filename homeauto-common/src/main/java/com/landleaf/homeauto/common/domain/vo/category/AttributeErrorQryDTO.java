package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 产品故障属性表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AttributeErrorQryDTO", description="AttributeErrorQryDTO")
public class AttributeErrorQryDTO {

    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = "属性code")
    private String code;


}
