package com.landleaf.homeauto.common.domain.po.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoProduct对象", description="产品表")
public class HomeAutoProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "品牌名称")
    private String brand;

    @ApiModelProperty(value = "产品编号")
    private String code;

    @ApiModelProperty(value = "品类id")
    private Integer categoryId;


}
