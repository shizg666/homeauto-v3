package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
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
@ApiModel(value="ProductAttributeScopeVO", description="产品属性范围对象")
public class ProductAttributeScopeVO {


    private static final long serialVersionUID = -1435592564049441356L;

    @ApiModelProperty(value = "精度 ")
    private Integer precision;

    @ApiModelProperty(value = "精度字符串")
    private String precisionStr;

    @ApiModelProperty(value = "属性范围最大值 ")
    private String max;

    @ApiModelProperty(value = "属性范围最小值")
    private String min;

    @ApiModelProperty(value = "属性范围步幅")
    private String step;

    public void setPrecision(Integer precision) {
        this.precision = precision;
        this.precisionStr = PrecisionEnum.getInstByType(precision) !=null ?PrecisionEnum.getInstByType(precision).getName():"";
    }
}
