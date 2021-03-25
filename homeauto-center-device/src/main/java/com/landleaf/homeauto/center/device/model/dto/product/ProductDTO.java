package com.landleaf.homeauto.center.device.model.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;
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
@ApiModel(value="ProductDTO", description="品类对象")
public class ProductDTO {


    private static final long serialVersionUID = -1693669149600857204L;
    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @NotEmpty(message = "品类id主键不能为空")
    @ApiModelProperty(value = "品类编码")
    private String categoryCode;

    @NotEmpty(message = "产品名称不能为空")
    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "品牌编号")
    private String brandCode;

    @NotEmpty(message = "产品编码不能为空")
    @ApiModelProperty(value = "产品编码")
    private String code;

    @ApiModelProperty(value = "产品型号")
    private String model;

    @NotEmpty(message = "产品图片不能为空")
    @ApiModelProperty(value = "产品图片（黑白，彩色需要解析）")
    private String icon;

    @ApiModelProperty(value = "协议主键id")
    private String protocolId;

    @ApiModelProperty(value = "备注")
    private String remark;

    //    @ApiModelProperty(value = "性质: 只读，控制")
//    private Integer nature;

//    @ApiModelProperty(value = "是否是暖通 0否1是")
//    private Integer hvacFlag;


//    @ApiModelProperty(value = "产品故障功能属性")
//    List<ProductAttributeErrorDTO> errorAttributes;


}
