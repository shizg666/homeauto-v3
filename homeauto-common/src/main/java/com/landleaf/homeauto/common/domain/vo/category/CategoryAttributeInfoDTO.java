package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="CategoryAttributeInfoDTO", description="品类属性值信息对象")
public class CategoryAttributeInfoDTO {

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

}
