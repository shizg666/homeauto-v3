package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.NonSmartRoomDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 自由方舟APP首页视图对象
 *
 * @author Yujiumin
 * @version 2020/8/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("自由方舟APP首页视图对象")
public class IndexOfNonSmartVO {

    @ApiModelProperty("暖通模式")
    private String hvacMode;

    @ApiModelProperty("环境参数")
    private EnvironmentVO environmentVO;

    @ApiModelProperty("常用场景")
    private List<SceneVO> commonScenes;

    @ApiModelProperty("房间设备")
    private List<NonSmartRoomDeviceVO> roomDevices;
}
