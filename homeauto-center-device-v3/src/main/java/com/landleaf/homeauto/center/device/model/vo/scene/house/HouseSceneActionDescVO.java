package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

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
@ApiModel(value="HouseSceneActionDescVO", description="HouseSceneActionDescVO")
public class HouseSceneActionDescVO {

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "动作")
    private String actionDesc;



}
