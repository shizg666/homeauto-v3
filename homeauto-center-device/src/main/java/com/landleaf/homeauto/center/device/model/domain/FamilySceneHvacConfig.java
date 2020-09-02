package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 场景暖通配置
 *
 * @author Yujiumin
 * @version 2020/9/2
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("family_scene_hvac_config")
public class FamilySceneHvacConfig extends BaseDO {

    @TableField("device_sn")
    private String deviceSn;

    @TableField("switch_code")
    private String switchCode;

    @TableField("switch_val")
    private String switchVal;

    @TableField("family_id")
    private String familyId;

    @TableField("scene_id")
    private String sceneId;

}
