package com.landleaf.homeauto.center.device.model.domain.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoProduct对象", description="产品表")
public class HomeAutoProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "品牌编号")
    private String brandCode;

    @ApiModelProperty(value = "产品编号")
    private String code;

    @ApiModelProperty(value = "产品图片")
    private String icon;

    @ApiModelProperty(value = "是否是暖通")
    private Integer hvacFlag;

    @ApiModelProperty(value = "协议类型")
    private Integer protocol;

    @ApiModelProperty(value = "性质类型 1控制，2只读")
    private Integer nature;

    @ApiModelProperty(value = "品类id")
    private String categoryId;


}
