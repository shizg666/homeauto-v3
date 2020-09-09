package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品故障属性表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AttributePrecisionQryDTO", description="精度信息")
public class AttributePrecisionDTO {


    @ApiModelProperty(value = "精度")
    /**
     * PrecisionEnum
     */
    private Integer precision;

    @ApiModelProperty(value = "属性code")
    private String code;


}
