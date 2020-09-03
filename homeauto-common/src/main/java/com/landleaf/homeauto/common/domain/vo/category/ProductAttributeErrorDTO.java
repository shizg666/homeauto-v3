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
@ApiModel(value="ProductAttributeError对象", description="产品故障属性表")
public class ProductAttributeErrorDTO  {

    @ApiModelProperty(value = "故障类型")
    private Integer type;

    @ApiModelProperty(value = "故障代码")
    private String code;

    @ApiModelProperty(value = "产品id")
    private String productId;


    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;

    @ApiModelProperty(value = "故障值")
    private List<ProductAttributeErrorInfoDTO> infos;


}
