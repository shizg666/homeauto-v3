package com.landleaf.homeauto.center.device.model.domain.category;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("product_attribute_error_info")
@ApiModel(value="ProductAttributeErrorInfo对象", description="")
public class ProductAttributeErrorInfo extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "故障值")
    private String val;

    @ApiModelProperty(value = "故障属性id")
    private Long errorAttributeId;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "产品id")
    private Long productId;

}
