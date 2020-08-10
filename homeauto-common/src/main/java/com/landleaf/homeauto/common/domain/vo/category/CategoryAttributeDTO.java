package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 属性字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CategoryAttributeDTO", description="类别属性对象")
public class CategoryAttributeDTO {

    @NotBlank(message = "属性名称不能为空")
    @ApiModelProperty(value = "属性名称")
    private String name;

    @NotBlank(message = "属性code不能为空")
    @ApiModelProperty(value = "属性code")
    private String code;

    @NotNull(message = "属性类别不能为空")
    @ApiModelProperty(value = "属性类别;单选，多选，值域")
    private Integer type;

    @NotNull(message = "性质不能为空")
    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "属性可选值")
    private List<CategoryAttributeInfoDTO> infos;


}
