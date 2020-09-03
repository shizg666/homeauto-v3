package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 场景暖通分室面板配置
 *
 * @author Yujiumin
 * @version 2020/9/2
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("family_scene_hvac_panel")
public class FamilySceneHvacConfigActionPanel extends BaseDO {

    @TableField("device_sn")
    private String deviceSn;

    @TableField("switch_code")
    private String switchCode;

    @TableField("switch_val")
    private String switchVal;

    @TableField("temperature_code")
    private String temperatureCode;

    @TableField("temperature_val")
    private String temperatureVal;

    @TableField("hvac_action_id")
    private String hvacActionId;

    @TableField("family_id")
    private String familyId;

}
