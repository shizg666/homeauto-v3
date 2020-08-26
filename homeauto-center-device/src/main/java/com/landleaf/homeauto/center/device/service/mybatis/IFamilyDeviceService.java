package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;

import java.util.List;
import java.util.Map;

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
    List<DeviceVO> getCommonDevicesByFamilyId(String familyId);

    /**
     * 通过家庭ID获取不常用的设备(也就是所有设备中,常用设备的补集)
     *
     * @param familyId 家庭ID
     * @return 不常用的设备集合
     */
    List<FamilyDevicesExcludeCommonVO> getUncommonDevicesByFamilyId(String familyId);

    /**
     * 通过设备序列号获取设备信息
     *
     * @param sceneId 场景ID
     * @return 设备信息集合
     */
    List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId);

    /**
     * 添加家庭常用设备
     *
     * @param familyDeviceCommonDTO 常用设备信息
     */
    void insertFamilyDeviceCommon(FamilyDeviceCommonDTO familyDeviceCommonDTO);

    /**
     * 根据产品id判断是否存在设备
     *
     * @param id
     * @return
     */
    boolean existByProductId(String id);

    /**
     * 根据房间ID获取设备列表
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    List<FamilyDeviceBO> getDeviceListByRoomId(String roomId);

    /**
     * 通过设备ID获取设备属性
     *
     * @param deviceId 设备ID
     * @return 设备属性
     */
    Map<String, Object> getDeviceAttributionsByDeviceId(String deviceId);

    /*
     * 根据产品id集合获取设备统计数据
     * @param productIds
     * @return
     */
    List<CountBO> getCountByProducts(List<String> productIds);


    /**
     * 统计家庭设备数量
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(List<String> familyIds);
}
