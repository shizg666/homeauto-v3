package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Yujiumin
 * @version 2020/8/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexForNonSmartVO {

    private EnvironmentVO environmentVO;

    private List<SceneVO> commonScenes;

    private List<DeviceVO> commonDevices;

    private Map<String, List<DeviceVO>> roomDevices;

}
