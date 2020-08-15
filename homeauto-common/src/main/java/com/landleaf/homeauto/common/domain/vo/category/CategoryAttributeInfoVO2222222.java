package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 属性值字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CategoryAttributeInfoVO2222222", description="类别属性值VO")
public class CategoryAttributeInfoVO2222222 {


    @NotBlank(message = "属性值名称不能为空")
    @ApiModelProperty(value = "属性值名称")
    private String name;

    @NotBlank(message = "属性值code不能为空")
    @ApiModelProperty(value = "属性值")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "是否选中 0否1是")
    private Integer Selected = 0;

}
