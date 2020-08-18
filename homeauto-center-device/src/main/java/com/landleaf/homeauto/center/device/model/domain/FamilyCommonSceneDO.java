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
 * 家庭常用场景表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "FamilyCommonScene对象", description = "家庭常用场景表")
@TableName("family_common_scene")
public class FamilyCommonSceneDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("sort_no")
    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @TableField("scene_id")
    @ApiModelProperty(value = "设备ID")
    private String sceneId;


}
