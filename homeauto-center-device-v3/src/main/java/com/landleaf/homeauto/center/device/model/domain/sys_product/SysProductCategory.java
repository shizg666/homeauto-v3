package com.landleaf.homeauto.center.device.model.domain.sys_product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统产品关联品类表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_product_category")
@ApiModel(value="Category对象", description="系统产品关联品类表")
public class SysProductCategory extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统产品id")
    private Long sysProductId;

    @ApiModelProperty(value = "系统产品code")
    private String sysProductCode;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "品类数量 0 1个 1多个")
    private Integer categoryNum;



}
