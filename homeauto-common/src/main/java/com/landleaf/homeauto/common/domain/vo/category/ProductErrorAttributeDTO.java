package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
@ApiModel(value="ProductErrorAttributeDTO", description="ProductErrorAttributeDTO")
public class ProductErrorAttributeDTO {


    @ApiModelProperty(value = "产品id")
    private String productId;


    @ApiModelProperty(value = "产品故障功能属性")
    List<ProductAttributeErrorDTO> errorAttributes;


}
