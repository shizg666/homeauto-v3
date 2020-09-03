package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 场景暖通模式配置
 *
 * @author Yujiumin
 * @version 2020/9/2
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("family_hvac_action")
public class FamilySceneHvacConfigAction extends BaseDO {

    @TableField("mode_code")
    private String modeCode;

    @TableField("mode_val")
    private String modeVal;

    @TableField("wind_code")
    private String windCode;

    @TableField("wind_val")
    private String windVal;

    @TableField("room_flag")
    private Integer roomFlag;

    @TableField("hvac_config_id")
    private String hvacConfigId;

    @TableField("switch_code")
    private String switchCode;

    @TableField("switch_val")
    private String switchVal;

    @TableField("temperature_code")
    private String temperatureCode;

    @TableField("temperature_val")
    private String temperatureVal;

}
