package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
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
    List<FamilyDeviceBO> getDeviceBOListByRoomId(String roomId);

    /**
     * 根据产品id集合获取设备统计数据
     *
     * @param productIds
     * @return
     */
    List<CountBO> getCountByProducts(List<String> productIds);

    /**
     * 通过家庭ID获取传感器业务对象
     *
     * @param familyId 家庭ID
     * @return
     */
    List<DeviceSensorBO> getDeviceSensorBOList(String familyId);

    /**
     * 统计家庭设备数量
     *
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(List<String> familyIds);

    /**
     * 批量获取设备
     *
     * @param deviceIds id列表
     * @return
     */
    List<FamilyDeviceDO> getDeviceListByIds(List<String> deviceIds);

    /**
     * 获取设备图标
     *
     * @param deviceId 设备ID
     * @return
     */
    String getDeviceIconById(String deviceId);

    /**
     * 获取设备位置
     *
     * @param deviceId 设备ID
     * @return
     */
    String getDevicePositionById(String deviceId);

    /**
     * 获取设备状态
     *
     * @param deviceSensorBO
     * @param attributeCode
     * @return
     */
    Object getDeviceStatus(DeviceSensorBO deviceSensorBO, String attributeCode);

    /**
     * 获取设备状态
     *
     * @param deviceId
     * @param attributeCode
     * @return
     */
    Object getDeviceStatus(String deviceId, String attributeCode);

    /**
     * 获取设备状态
     *
     * @param familyCode
     * @param productCode
     * @param deviceSn
     * @param attributeCode
     * @return
     */
    Object getDeviceStatus(String familyCode, String productCode, String deviceSn, String attributeCode);

    /**
     * 获取房间设备列表
     *
     * @param roomId 房间ID
     * @return
     */
    List<FamilyDeviceDO> getDeviceListByRoomId(String roomId);

    /**
     * 获取家庭的甲醛传感器
     *
     * @param familyId 家庭ID
     * @return 甲醛传感器
     */
    DeviceSensorBO getHchoSensor(String familyId);

    /**
     * 获取家庭的pm2.5传感器
     *
     * @param familyId 家庭ID
     * @return pm2.5传感器
     */
    DeviceSensorBO getPm25Sensor(String familyId);

    /**
     * 获取全参传感器
     *
     * @param familyId 家庭ID
     * @return 全参传感器
     */
    DeviceSensorBO getAllParamSensor(String familyId);

    /**
     * 获取多参传感器
     *
     * @param familyId
     * @return
     */
    DeviceSensorBO getMultiParamSensor(String familyId);
}
