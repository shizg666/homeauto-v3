package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品属性值表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProductAttributeInfo对象", description="产品属性值表")
@TableName("product_attribute_info")
public class ProductAttributeInfoDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "属性值名称")
    private String name;

    @TableField("code")
    @ApiModelProperty(value = "属性值")
    private String code;

    @TableField("product_attribute_id")
    @ApiModelProperty(value = "属性id")
    private String productAttributeId;

    @TableField("sort_no")
    @ApiModelProperty(value = "排序号")
    private Integer sortNo;


}
