package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProductUpdateDTO", description="产品修改对象")
public class ProductUpdateDTO {


    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "品牌编号")
    private String brandCode;

    @ApiModelProperty(value = "产品型号")
    private String model;

//    @ApiModelProperty(value = "性质: 只读，控制")
//    private Integer nature;
//
//    @ApiModelProperty(value = "协议")
//    private Integer protocol;
//
//    @ApiModelProperty(value = "是否是暖通 0否1是")
//    private Integer hvacFlag;



}
