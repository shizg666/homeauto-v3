package com.landleaf.homeauto.center.device.model.domain.sysproduct;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysCategoryAttribute对象", description="")
public class SysCategoryAttribute extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统产品名称")
    private String name;

    @ApiModelProperty(value = "系统产品code")
    private String code;

    @ApiModelProperty(value = "系统和吵闹")
    private Integer type;

    @ApiModelProperty(value = "属性类型 1控制 2只读")
    private Integer nature;

    private Integer sortNo;

    @ApiModelProperty(value = "功能类型 1基本属性 2 功能属性")
    private Integer functionType;

    @ApiModelProperty(value = "系统产品id")
    private Long sysProductId;

    @ApiModelProperty(value = "系统产品code")
    private String sysProductCode;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;


}
