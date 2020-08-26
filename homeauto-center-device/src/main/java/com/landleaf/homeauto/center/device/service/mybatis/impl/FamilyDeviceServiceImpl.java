package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家庭设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyDeviceServiceImpl extends ServiceImpl<FamilyDeviceMapper, FamilyDeviceDO> implements IFamilyDeviceService {

    private FamilyDeviceMapper familyDeviceMapper;

    private IFamilyDeviceStatusService familyDeviceStatusService;

    private IFamilyCommonDeviceService familyCommonDeviceService;

    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    @Override
    public List<DeviceVO> getCommonDevicesByFamilyId(String familyId) {
        List<FamilyDeviceWithPositionBO> allDeviceBOList = familyDeviceMapper.getAllDevicesByFamilyId(familyId);
        List<FamilyDeviceWithPositionBO> commonDeviceBOList = familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
        allDeviceBOList.removeIf(commonDeviceBO -> !commonDeviceBOList.contains(commonDeviceBO));
        List<DeviceVO> deviceVOList = new LinkedList<>();
        for (FamilyDeviceWithPositionBO commonDeviceBO : allDeviceBOList) {
            DeviceVO deviceVO = new DeviceVO();
            deviceVO.setDeviceId(commonDeviceBO.getDeviceId());
            deviceVO.setDeviceName(commonDeviceBO.getDeviceName());
            deviceVO.setPosition(getPosition(commonDeviceBO));
            deviceVO.setDeviceIcon(commonDeviceBO.getDeviceIcon());
            deviceVO.setIndex(commonDeviceBO.getIndex());
            deviceVOList.add(deviceVO);
        }
        for (DeviceVO commonDeviceVO : deviceVOList) {
            // TODO: 设备的开关状态
            // 一期砍掉,暂时不做
        }
        return deviceVOList;
    }

    @Override
    public List<FamilyDevicesExcludeCommonVO> getUncommonDevicesByFamilyId(String familyId) {
        // 获取家庭所有的设备
        List<FamilyDeviceWithPositionBO> allDeviceList = familyDeviceMapper.getAllDevicesByFamilyId(familyId);
        Map<String, List<FamilyDeviceWithPositionBO>> map = new LinkedHashMap<>();
        // 先将所有的设备按位置分类
        for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : allDeviceList) {
            // 位置信息: 楼层-房间
            String position = getPosition(familyDeviceWithPositionBO);
            if (map.containsKey(position)) {
                map.get(position).add(familyDeviceWithPositionBO);
            } else {
                map.put(position, CollectionUtil.list(true, familyDeviceWithPositionBO));
            }
        }
        // 到这里,设备已经按房间分好类

        // 获取家庭常用设备
        List<FamilyDeviceWithPositionBO> commonDeviceList = familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
        for (FamilyDeviceWithPositionBO commonDevice : commonDeviceList) {
            // 从全部设备中移除所有常用设备
            map.get(getPosition(commonDevice)).remove(commonDevice);
        }

        // 到这一步按房间把常用设备移除了

        // 现在这里的只有不常用的设备了,即使是房间内没有设备,也会显示空数组
        List<FamilyDevicesExcludeCommonVO> familyDevicesExcludeCommonVOList = new LinkedList<>();
        for (String key : map.keySet()) {
            List<DeviceVO> deviceVOList = new LinkedList<>();
            List<FamilyDeviceWithPositionBO> familyDeviceBOList = map.get(key);
            for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : familyDeviceBOList) {
                DeviceVO deviceVO = new DeviceVO();
                deviceVO.setDeviceId(familyDeviceWithPositionBO.getDeviceId());
                deviceVO.setDeviceName(familyDeviceWithPositionBO.getDeviceName());
                deviceVO.setDeviceIcon(familyDeviceWithPositionBO.getDeviceIcon());
                deviceVO.setIndex(familyDeviceWithPositionBO.getIndex());
                deviceVO.setPosition(getPosition(familyDeviceWithPositionBO));
                deviceVOList.add(deviceVO);
            }
            FamilyDevicesExcludeCommonVO familyDevicesExcludeCommonVO = new FamilyDevicesExcludeCommonVO();
            familyDevicesExcludeCommonVO.setPositionName(key);
            familyDevicesExcludeCommonVO.setDevices(deviceVOList);
            familyDevicesExcludeCommonVOList.add(familyDevicesExcludeCommonVO);
        }
        return familyDevicesExcludeCommonVOList;
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId) {
        return familyDeviceMapper.getDeviceInfoByDeviceSn(sceneId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertFamilyDeviceCommon(FamilyDeviceCommonDTO familyDeviceCommonDTO) {
        // 先删除原来的常用设备
        QueryWrapper<FamilyCommonDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyDeviceCommonDTO.getFamilyId());
        familyCommonDeviceService.remove(queryWrapper);

        // 再把新的常用设备添加进去
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = new LinkedList<>();
        for (String deviceId : familyDeviceCommonDTO.getDevices()) {
            FamilyCommonDeviceDO familyCommonSceneDO = new FamilyCommonDeviceDO();
            familyCommonSceneDO.setFamilyId(familyDeviceCommonDTO.getFamilyId());
            familyCommonSceneDO.setDeviceId(deviceId);
            familyCommonSceneDO.setSortNo(0);
            familyCommonDeviceDOList.add(familyCommonSceneDO);
        }
        familyCommonDeviceService.saveBatch(familyCommonDeviceDOList);
    }

    @Autowired
    public void setFamilyDeviceStatusService(IFamilyDeviceStatusService familyDeviceStatusService) {
        this.familyDeviceStatusService = familyDeviceStatusService;
    }

    @Override
    public boolean existByProductId(String productId) {
        QueryWrapper<FamilyDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.last("limit 1");
        Integer deviceCount = baseMapper.selectCount(queryWrapper);
        return deviceCount > 0;
    }

    @Override
    public List<FamilyDeviceBO> getDeviceListByRoomId(String roomId) {
        return familyDeviceMapper.getDeviceListByRoomId(roomId);
    }

    @Override
    public Map<String, Object> getDeviceAttributionsByDeviceId(String deviceId) {
        List<FamilyDeviceStatusDO> familyDeviceStatusDOList = familyDeviceStatusService.getDeviceAttributionStatusById(deviceId);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        for (FamilyDeviceStatusDO familyDeviceStatusDO : familyDeviceStatusDOList) {
            String statusCode = familyDeviceStatusDO.getStatusCode();
            String statusValue = familyDeviceStatusDO.getStatusValue();
            attrMap.put(statusCode, statusValue);
        }
        return attrMap;
    }

    @Override
    public List<CountBO> getCountByProducts(List<String> productIds) {
        return this.baseMapper.getCountByProducts(productIds);
    }

    @Override
    public Map<String, Object> getDeviceSensorListByFamilyId(String familyId) {
        List<DeviceSensorBO> deviceSensorList = familyDeviceMapper.getDeviceSensorList(familyId);
        Map<String, Object> deviceStatusMap = new LinkedHashMap<>();
        for (DeviceSensorBO deviceSensorBO : deviceSensorList) {
            String familyCode = deviceSensorBO.getFamilyCode();
            String productCode = deviceSensorBO.getProductCode();
            String deviceSn = deviceSensorBO.getDeviceSn();
            String statusCode = deviceSensorBO.getStatusCode();
            String deviceStatusKey = RedisKeyUtils.getDeviceStatusKey(familyCode, productCode, deviceSn, statusCode);
            Object deviceStatus = redisServiceForDeviceStatus.getDeviceStatus(deviceStatusKey);
            deviceStatusMap.put(statusCode, deviceStatus);
        }
        return deviceStatusMap;
    }

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Autowired
    public void setFamilyDeviceMapper(FamilyDeviceMapper familyDeviceMapper) {
        this.familyDeviceMapper = familyDeviceMapper;
    }

    @Autowired
    public void setFamilyCommonDeviceService(IFamilyCommonDeviceService familyCommonDeviceService) {
        this.familyCommonDeviceService = familyCommonDeviceService;
    }

    @Autowired
    public void setRedisServiceForDeviceStatus(RedisServiceForDeviceStatus redisServiceForDeviceStatus) {
        this.redisServiceForDeviceStatus = redisServiceForDeviceStatus;
    }

    /**
     * 获取设备位置
     *
     * @param familyDeviceWithPositionBO 带有位置信息的设备业务对象
     * @return 房间位置
     */
    private String getPosition(FamilyDeviceWithPositionBO familyDeviceWithPositionBO) {
        return String.format("%s-%s", familyDeviceWithPositionBO.getFloorName(), familyDeviceWithPositionBO.getRoomName());
    }

}
