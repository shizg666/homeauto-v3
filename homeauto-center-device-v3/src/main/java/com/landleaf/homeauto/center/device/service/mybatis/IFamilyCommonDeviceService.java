package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyAllDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO;

import java.util.List;

/**
 * <p>
 * 家庭常用设备表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyCommonDeviceService extends IService<FamilyCommonDeviceDO> {

    /**
     * 获取家庭常用设备ID列表
     *
     * @param familyId 家庭ID
     * @return 常用设备ID列表
     */
    List<FamilyCommonDeviceDO> listByFamilyId(Long familyId);

    /**
     * 通过familyId删除常用设备
     *
     * @param familyId 家庭ID
     */
    void deleteFamilyCommonDeviceList(Long familyId);
    /**
     * APP保存常用设备
     * @param familyId 家庭ID
     * @param devices  设备IDs
     * @return void
     * @author wenyilu
     * @date  2021/1/6 10:55
     */
    void saveCommonDeviceList(Long familyId, List<Long> devices);
    /**
     * 获取APP常用设备
     * @param familyId    家庭ID
     * @param templateId  户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO>
     * @author wenyilu
     * @date  2020/12/25 11:34
     */
    List<FamilyDeviceVO> getCommonDevicesByFamilyId4VO(Long familyId, Long templateId);

    /**
     * APP获取不常用设备
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO>
     * @author wenyilu
     * @date  2021/1/7 13:54
     */
    List<FamilyUncommonDeviceVO> getUnCommonDevices4VO(Long familyId);

    /**
     * 获取所有设备
     * @param familyId
     * @return
     */
    List<FamilyAllDeviceVO> getAllDevices4AppletsVO(Long familyId);
}
