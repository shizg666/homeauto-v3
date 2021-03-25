package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.dto.TimingSceneAppletsDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO;
import com.landleaf.homeauto.center.device.model.vo.scene.AppletsSceneTimingDetailVO;

/**
 * @author pilo
 */
public interface AppletsService {
    /**
     * 保存定时场景
     * @param timingSceneDTO
     */
    boolean saveTimingScene(TimingSceneAppletsDTO timingSceneDTO);
    /**
     *  小程序获取设备属性及值
     * @param familyId
     * @param deviceId
     * @return com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO
     * @author wenyilu
     * @date 2021/3/15 15:50
     */
    AppletsDeviceInfoVO getDeviceStatus4AppletsVO(String familyId, String deviceId);
    /**
     *  小程序查看定时场景信息
     * @param timingId
     * @return com.landleaf.homeauto.center.device.model.vo.scene.AppletsSceneTimingDetailVO
     * @author wenyilu
     * @date 2021/3/19 15:17
     */
    AppletsSceneTimingDetailVO getTimingSceneDetail4Applets(String timingId);
}
