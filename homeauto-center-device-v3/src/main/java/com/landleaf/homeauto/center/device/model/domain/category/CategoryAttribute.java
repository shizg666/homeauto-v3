package com.landleaf.homeauto.center.device.model.domain.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品类属性信息表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CategoryAttribute对象", description="品类属性信息表")
public class CategoryAttribute extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "品类id")
    private Long categoryId;

    @ApiModelProperty(value = "功能类别 1功能属性 2 基本属性")
    private Integer functionType;


}
