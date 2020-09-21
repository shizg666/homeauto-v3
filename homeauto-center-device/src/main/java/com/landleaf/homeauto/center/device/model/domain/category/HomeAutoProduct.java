package com.landleaf.homeauto.center.device.model.domain.category;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("home_auto_product")
public class HomeAutoProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "型号")
    @TableField("model")
    private String model;

    @ApiModelProperty(value = "品牌编号")
    @TableField("brand_code")
    private String brandCode;

    @ApiModelProperty(value = "产品编号")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "产品图片")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "是否是暖通")
    @TableField("hvac_flag")
    private Integer hvacFlag;

    @ApiModelProperty(value = "协议类型")
    @TableField("protocol")
    private Integer protocol;

    @ApiModelProperty(value = "性质类型 1控制，2只读")
    @TableField("nature")
    private Integer nature;

    @ApiModelProperty(value = "品类id")
    @TableField("category_id")
    private String categoryId;



}
