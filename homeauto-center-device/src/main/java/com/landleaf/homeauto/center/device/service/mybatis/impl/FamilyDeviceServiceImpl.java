package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
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

    @Autowired
    private FamilyDeviceMapper familyDeviceMapper;

    @Override
    public List<FamilyDeviceWithPositionBO> getAllDevices(String familyId) {
        return familyDeviceMapper.getAllDevicesByFamilyId(familyId);
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getCommonDevices(String familyId) {
        return familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId) {
        return familyDeviceMapper.getDeviceInfoByDeviceSn(sceneId);
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
    public List<CountBO> getCountByProducts(List<String> productIds) {
        return this.baseMapper.getCountByProducts(productIds);
    }

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public List<FamilyDeviceDO> getDeviceListByIds(List<String> deviceIds) {
        return null;
    }

    @Override
    public String getDeviceIconById(String deviceId) {
        return null;
    }

    @Override
    public String getDevicePositionById(String deviceId) {
        return null;
    }

    @Override
    public Object getDeviceStatus(DeviceSensorBO deviceSensorBO, String attributeCode) {
        return null;
    }

    @Override
    public Object getDeviceStatus(String deviceId, String statusCode) {
        return null;
    }

    @Override
    public Object getDeviceStatus(String familyCode, String productCode, String deviceSn, String attributeCode) {
        return null;
    }

    @Override
    public List<FamilyDeviceDO> getDeviceListByRoomId(String roomId) {
        return null;
    }

    @Override
    public List<FamilyDeviceBO> getDeviceInfoListByRoomId(String roomId) {
        return familyDeviceMapper.getDeviceListByRoomId(roomId);
    }

    @Override
    public DeviceSensorBO getHchoSensor(String familyId) {
        return null;
    }

    @Override
    public DeviceSensorBO getPm25Sensor(String familyId) {
        return null;
    }

    @Override
    public DeviceSensorBO getAllParamSensor(String familyId) {
        return null;
    }

    @Override
    public DeviceSensorBO getMultiParamSensor(String familyId) {
        return null;
    }

}
