package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDeviceVO;

import java.util.List;

/**
 * <p>
 * 家庭设备表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyDeviceService extends IService<FamilyDeviceDO> {

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 常用场景列表
     */
    List<FamilyDeviceVO> getCommonDevicesByFamilyId(String familyId);

    /**
     * 通过家庭ID获取不常用的设备(也就是所有设备中,常用设备的补集)
     *
     * @param familyId 家庭ID
     * @return 不常用的设备集合
     */
    List<FamilyDeviceVO> getUncommonDevicesByFamilyId(String familyId);

    /**
     * 通过设备序列号获取设备信息
     *
     * @param sceneId 场景ID
     * @return 设备信息集合
     */
    List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId);

}
