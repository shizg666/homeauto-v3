package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 场景关联设备动作表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "FamilySceneAction对象", description = "场景关联设备动作表")
@TableName("family_scene_action")
public class FamilySceneActionDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("scene_id")
    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @TableField("device_sn")
    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @TableField("product_attribute_id")
    @ApiModelProperty(value = "产品属性id")
    private String productAttributeId;

    @TableField("product_attribute_code")
    @ApiModelProperty(value = "产品属性code")
    private String productAttributeCode;

    @TableField("val")
    @ApiModelProperty(value = "属性值")
    private String val;

    @TableField("operator")
    @ApiModelProperty(value = "比较和状态: 0-等于 1-大于 2大于等于 -1-小于 -2-小于等于")
    private Integer operator;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭id")
    private String familyId;



}
