package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.dto.TimingSceneAppletsDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsDeviceInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyAllDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoAppletsVO;
import com.landleaf.homeauto.center.device.model.vo.scene.AppletsSceneTimingDetailVO;

import java.util.List;

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
    /**
     * 小程序我的家庭-获取楼层房间设备信息
     *获取某个家庭详情：楼层、房间、设备、用户信息等
     * @param familyId  家庭ID
     * @return com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO
     * @author wenyilu
     * @date 2020/12/25 15:00
     */
    MyFamilyDetailInfoAppletsVO getMyFamilyInfo4Applets(String familyId, String userId);

    List<FamilyAllDeviceVO> getAllDevices(String familyId);
}
