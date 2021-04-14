package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="ProductAttributeError对象", description="产品故障属性表")
public class ProductAttributeErrorDTO  {

    @ApiModelProperty(value = "主键id 修改必传")
    private Long id;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品code")
    private String productCode;

    /**
     * AttributeErrorTypeEnum
     */
    @ApiModelProperty(value = "故障类型 1 故障码 2通信 3 数值")
    private Integer type;

    @ApiModelProperty(value = "故障代码")
    private String code;

    @ApiModelProperty(value = "故障代码名称")
    private String codeName;

    @ApiModelProperty(value = "最大值 （故障类型 3 数值）")
    private String max;

    @ApiModelProperty(value = "最小值 （故障类型 3 数值）")
    private String min;

    @ApiModelProperty(value = "正常值 （故障类型 2 通信）")
    private Integer normalVal;

    @ApiModelProperty(value = "异常值 （故障类型 2 通信）")
    private Integer unnormalVal;

    @ApiModelProperty(value = "故障值 （故障类型 1 故障码）")
    private List<ProductAttributeErrorInfoDTO> infos;




}
