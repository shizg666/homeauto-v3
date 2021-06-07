package com.landleaf.homeauto.center.device.model.domain.sysproduct;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统产品故障信息表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_product_attribute_error_info")
@ApiModel(value="AttributeErrorInfo对象", description="系统产品故障信息表")
public class SysProductAttributeErrorInfo extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统产品故障描述")
    private String val;

    @ApiModelProperty(value = "系统故障属性id")
    private Long errorAttributeId;

    @ApiModelProperty(value = "系统产品id")
    private Long sysProductId;

    @ApiModelProperty(value = "系统产品code")
    private String sysProductCode;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;



}
