package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
@ApiModel(value="HouseSceneDTO", description="场景非暖通设备动作信息")
public class SceneDeviceActionDTO {
//    @ApiModelProperty(value = "场景id")
//    private String sceneId;

    @ApiModelProperty(value = "设备号")
    private String sn;

    @ApiModelProperty(value = "属性列表")
    List<SceneDeviceActionInfoDTO> children;

}
