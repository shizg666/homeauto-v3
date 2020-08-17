package com.landleaf.homeauto.common.domain.po.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品属性精度范围表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProductAttributeInfoScope对象", description="产品属性精度范围表")
public class ProductAttributeInfoScope extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型1属性2是属性值")
    private Integer type;

    @ApiModelProperty(value = "精度 ")
    private Integer precision;

    @ApiModelProperty(value = "属性范围最大值 ")
    private String max;

    @ApiModelProperty(value = "属性范围最小值")
    private String min;

    @ApiModelProperty(value = "属性范围步幅")
    private String step;

    @ApiModelProperty(value = "属性/属性值id")
    private String parentId;


}
