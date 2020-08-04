package com.landleaf.homeauto.common.domain.po.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品类属性值表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_category_attribute_info")
@ApiModel(value="HomeAutoCategoryAttributeInfo对象", description="品类属性值表")
public class HomeAutoCategoryAttributeInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String val;

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "排序")
    private Integer order;


}
