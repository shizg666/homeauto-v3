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
@ApiModel(value="CategoryAttributeDTO", description="品类属性信息对象")
public class CategoryAttributeDTO {

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "功能类型")
    private Integer functionType;

    @ApiModelProperty(value = "属性值")
    List<CategoryAttributeInfoDTO> infos;

}
