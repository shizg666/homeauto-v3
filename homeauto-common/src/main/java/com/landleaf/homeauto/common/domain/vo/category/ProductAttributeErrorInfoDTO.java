package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@Accessors(chain = true)
@ApiModel(value="ProductAttributeErrorInfo对象", description="")
public class ProductAttributeErrorInfoDTO {


    @ApiModelProperty(value = "故障值")
    private String val;


    @ApiModelProperty(value = "序号")
    private Integer sortNo;


}
