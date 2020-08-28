package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 场景暖通动作配置
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("house_scene_hvac_action")
@ApiModel(value="HvacAction对象", description="场景暖通动作配置")
public class HvacAction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模式code")
    private String modeCode;

    @ApiModelProperty(value = "模式值")
    private String modeVal;

    @ApiModelProperty(value = "风量code")
    private String windCode;

    @ApiModelProperty(value = "风量值")
    private String windVal;

    @ApiModelProperty(value = "是否是分室 0否1是")
    @TableField("roomFlag")
    private Integer roomFlag;

    @ApiModelProperty(value = "暖通配置主键")
    private String hvacConfigId;


}
