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
@ApiModel(value="CategoryAttrVO", description="品类属性对象")
public class CategoryAttrVO {

    @ApiModelProperty(value = "品类功能属性集合")
    private List<CategoryAttributeVO> attributes1;

    @ApiModelProperty(value = "品类基本属性集合")
    private List<CategoryAttributeVO> attributes2;


}
