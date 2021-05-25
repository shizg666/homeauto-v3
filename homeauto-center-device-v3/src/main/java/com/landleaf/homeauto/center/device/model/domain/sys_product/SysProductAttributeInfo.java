package com.landleaf.homeauto.center.device.model.domain.sys_product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统产品属性值表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_product_attribute_info")
@ApiModel(value="AttributeInfo对象", description="系统产品属性值表")
public class SysProductAttributeInfo extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String code;

    @ApiModelProperty(value = "属性id")
    private Long sysAttrId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "系统产品code")
    private String sysProductCode;

    @ApiModelProperty(value = "系统产品id")
    private Long sysProductId;



}
