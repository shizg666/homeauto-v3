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
 * 家庭情景表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyScene对象", description="家庭情景表")
@TableName("family_scene")
public class FamilySceneDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "情景名称")
    private String name;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @TableField("type")
    @ApiModelProperty(value = "场景类型1 全屋场景 2 智能场景")
    private Integer type;

    @TableField("default_flag")
    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @TableField("update_flag_screen")
    @ApiModelProperty(value = "大屏是否可修改 1是 0否 ")
    private Integer updateFlagScreen;

    @TableField("hvac_flag")
    @ApiModelProperty("是否有暖通")
    private Integer hvacFlag;

    @TableField("update_flag_app")
    @ApiModelProperty(value = "app是否可修改 1是 0否 ")
    private Integer updateFlagApp;

    @TableField("icon")
    @ApiModelProperty(value = "场景图标")
    private String icon;


    @TableField("mode")
    @ApiModelProperty(value = "模式")
    private Integer mode;

    @ApiModelProperty(value = "场景编号")
    private String sceneNo;

}
