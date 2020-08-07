package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@ApiModel(value="AttributeDicDTO", description="属性字典表")
public class AttributeDicDTO {

    @ApiModelProperty(value = "新增必填")
    private String id;

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
    private List<AttributeInfoDicDTO> infos;


}
