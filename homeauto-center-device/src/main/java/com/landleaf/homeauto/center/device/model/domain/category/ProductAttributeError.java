package com.landleaf.homeauto.center.device.model.domain.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品故障属性表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProductAttributeError对象", description="产品故障属性表")
public class ProductAttributeError extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "故障类型")
    private Integer type;

    @ApiModelProperty(value = "故障代码")
    private String code;

    @ApiModelProperty(value = "故障代码名称（冗余字段）")
    private String codeName;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;


}
