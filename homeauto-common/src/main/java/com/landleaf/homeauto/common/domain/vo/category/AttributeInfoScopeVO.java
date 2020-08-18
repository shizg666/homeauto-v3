package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@Accessors(chain = true)
@ApiModel(value="AttributeInfoScopeVO", description="产品属性值范围对象")
public class AttributeInfoScopeVO {

    @ApiModelProperty(value = "属性范围最大值 ")
    private String max;

    @ApiModelProperty(value = "属性范围最小值")
    private String min;
}
