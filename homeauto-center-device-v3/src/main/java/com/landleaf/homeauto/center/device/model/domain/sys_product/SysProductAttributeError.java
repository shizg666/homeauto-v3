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
 * 系统产品故障属性表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_product_attribute_error")
@ApiModel(value="AttributeError对象", description="系统产品故障属性表")
public class SysProductAttributeError extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "故障类型 1 故障码 2通信 3数值")
    private Integer type;

    @ApiModelProperty(value = "故障代码")
    private String code;

    @ApiModelProperty(value = "最大值 故障类型3")
    private String max;

    @ApiModelProperty(value = "最小值 故障类型3")
    private String min;

    @ApiModelProperty(value = "故障代码名称（冗余字段）")
    private String codeName;

    @ApiModelProperty(value = "系统产品编码")
    private String sysProductCode;

    @ApiModelProperty(value = "正常数值  故障类型2")
    private Integer normalVal;

    @ApiModelProperty(value = "不正常数值 故障类型2")
    private Integer unNormalVal;

    @ApiModelProperty(value = "系统产品id")
    private Long sysProductId;



}
