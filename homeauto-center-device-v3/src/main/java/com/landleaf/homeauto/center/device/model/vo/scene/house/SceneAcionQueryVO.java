package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 场景联动关备属性表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-22
 */
@Data
@Accessors(chain = true)
@ApiModel(value="情景动作查询对象", description="")
public class SceneAcionQueryVO {

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "场景id")
    private String sceneId;


}
