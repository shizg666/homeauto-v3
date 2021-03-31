package com.landleaf.homeauto.center.device.model.domain.category;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品属性信息表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProductAttribute对象", description="产品属性信息表")
@TableName("product_attribute")
public class ProductAttributeDO extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "属性名称")
    private String name;

    @TableField("code")
    @ApiModelProperty(value = "属性code")
    private String code;

    @TableField("type")
    @ApiModelProperty(value = "属性类别;单选，多选，值域，特殊多选值")
    private Integer type;

    @TableField("nature")
    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @TableField("product_id")
    @ApiModelProperty(value = "产品id")
    private Long productId;

    @TableField("product_code")
    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = "排序号")
    private Integer sortNo;

    @ApiModelProperty(value = "功能类别 1功能 2基本")
    @TableField("function_type")
    private Integer functionType;




}
