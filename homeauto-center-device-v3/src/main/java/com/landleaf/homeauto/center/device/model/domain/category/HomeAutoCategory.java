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
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoCategory对象", description="品类表")
public class HomeAutoCategory extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品类名称")
    private String name;

    @ApiModelProperty(value = "品类编码")
    private String code;

    @ApiModelProperty(value = "状态 0 启用 1停用")
    private Integer status;


}
