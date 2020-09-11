package com.landleaf.homeauto.center.device.model.vo.scene.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilySceneDetailQryDTO", description="场景详情查询入参")
public class FamilySceneDetailQryDTO {
    @ApiModelProperty(value = "场景主键id")
    private String id;

    @ApiModelProperty(value = "家庭id")
    private String familyId;



}
