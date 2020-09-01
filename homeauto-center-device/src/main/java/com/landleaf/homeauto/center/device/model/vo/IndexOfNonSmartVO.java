package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
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

    @ApiModelProperty("环境参数")
    private EnvironmentVO environmentVO;

    @ApiModelProperty("常用场景")
    private List<SceneVO> commonScenes;

    @ApiModelProperty("常用设备")
    private List<DeviceVO> commonDevices;

    @ApiModelProperty("房间设备")
    private Map<String, List<DeviceVO>> roomDevices;

}
