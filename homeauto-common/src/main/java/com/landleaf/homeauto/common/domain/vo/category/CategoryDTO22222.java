package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
@ApiModel(value="CategoryDTO", description="品类对象")
public class CategoryDTO22222 {


    private static final long serialVersionUID = -1693669149600857204L;
    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @NotNull(message = "品类类型不能为空")
    @ApiModelProperty(value = "品类类型")
    private Integer type;

    @NotNull(message = "性质类型不能为空")
    @ApiModelProperty(value = "性质: 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "协议以,分隔 ps 1,2")
    private Integer protocol;

    @ApiModelProperty(value = "功能属性")
    List<CategoryAttributeDTO2222222> attributes;


}
