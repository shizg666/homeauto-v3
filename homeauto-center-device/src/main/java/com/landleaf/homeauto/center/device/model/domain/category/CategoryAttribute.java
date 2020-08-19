package com.landleaf.homeauto.center.device.model.domain.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
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
public class CategoryAttribute extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "品类id")
    private String categoryId;


}
