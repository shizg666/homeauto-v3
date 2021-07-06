package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 三方家庭场景icon表
 * </p>
 *
 * @author lokiy
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ThirdFamilySceneIcon对象", description="三方家庭场景icon表")
public class ThirdFamilySceneIcon extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭场景id")
    private Long sceneId;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "家庭id")
    private Long familyId;


}
