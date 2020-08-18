package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
public class CategoryDTO {


    private static final long serialVersionUID = -1693669149600857204L;
    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "品类名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "品类编码")
    private String code;

    @ApiModelProperty(value = "协议 ,号分隔 ps 1,2")
    private String protocol;

    @ApiModelProperty(value = "功能属性")
    List<String> attributes;



}
