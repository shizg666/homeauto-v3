package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="HouseSceneDeviceConfigBO", description="户型场景设备配置")
public class HouseSceneDeviceConfigBO {

//    @ApiModelProperty(value = "主键id")
//    private String id;

    @ApiModelProperty(value = "关联的id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备动作列表")
    private List<HouseSceneDeviceActionBO> actions;



}
