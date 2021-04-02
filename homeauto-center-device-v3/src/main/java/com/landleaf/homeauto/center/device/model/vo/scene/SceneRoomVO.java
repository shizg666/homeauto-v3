package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneRoomVO", description="场景房间信息")
public class SceneRoomVO {

    @ApiModelProperty("房间名称")
    private String name;

    @ApiModelProperty(value = "设备集合")
    List<SceneDeviceVO> devices;

}
