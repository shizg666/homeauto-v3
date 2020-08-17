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
@ApiModel(value="CategoryDTO", description="品类对象")
public class ProductDTO {


    private static final long serialVersionUID = -1693669149600857204L;
    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "品类类型")
    private Integer type;

    @ApiModelProperty(value = "性质: 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "协议")
    private Integer protocol;

    @ApiModelProperty(value = "波特率")
    private Integer baudRate;

    @ApiModelProperty(value = "数据位")
    private String dataBit;

    @ApiModelProperty(value = "停止位")
    private String stopBit;

    @ApiModelProperty(value = "校验模式")
    private Integer checkMode;

    @ApiModelProperty(value = "功能属性")
    List<CategoryAttributeDTO2222222> attributes;


}
