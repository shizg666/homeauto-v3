package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceStatusMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Service
public class FamilyDeviceStatusServiceImpl extends ServiceImpl<FamilyDeviceStatusMapper, FamilyDeviceStatusDO> implements IFamilyDeviceStatusService {

    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    private IHomeAutoProductService homeAutoProductService;

    @Override
    public List<FamilyDeviceStatusDO> getDeviceAttributionsBySn(String deviceSn) {
        QueryWrapper<FamilyDeviceStatusDO> familyDeviceStatusQueryWrapper = new QueryWrapper<>();
        familyDeviceStatusQueryWrapper.eq("device_sn", deviceSn);
        return list(familyDeviceStatusQueryWrapper);
    }

    @Override
    public void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList) {
        for (DeviceStatusBO deviceStatusBO : deviceStatusBOList) {
            String familyCode = deviceStatusBO.getFamilyCode();
            String productCode = deviceStatusBO.getProductCode();
            String deviceSn = deviceStatusBO.getDeviceSn();
            String statusCode = deviceStatusBO.getStatusCode();
            String statusValue = deviceStatusBO.getStatusValue();
            String key = RedisKeyUtils.getDeviceStatusKey(familyCode, productCode, deviceSn, statusCode);
            Object deviceStatus = redisServiceForDeviceStatus.getDeviceStatus(key);
            if (!Objects.isNull(deviceStatus) && Objects.equals(deviceStatus.toString(), statusValue)) {
                // 如果设备的上次状态和上报状态一致,则更新状态的结束时间
                UpdateWrapper<FamilyDeviceStatusDO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("end_time", LocalDateTime.now());
                updateWrapper.eq("family_code", familyCode);
                updateWrapper.eq("product_code", productCode);
                updateWrapper.eq("device_sn", deviceSn);
                updateWrapper.eq("status_code", statusCode);
                update(updateWrapper);
            } else {
                // 如果设备的上次状态和上报状态不一致,则插入一条新的状态
                FamilyDeviceStatusDO familyDeviceStatusDO = new FamilyDeviceStatusDO();
                familyDeviceStatusDO.setDeviceSn(deviceSn);
                familyDeviceStatusDO.setStatusCode(statusCode);
                familyDeviceStatusDO.setStatusValue(statusValue);
                familyDeviceStatusDO.setFamilyId(deviceStatusBO.getFamilyId());
                familyDeviceStatusDO.setProductCode(productCode);
                familyDeviceStatusDO.setCategoryCode(homeAutoProductService.getCategoryCodeByProductCode(productCode));
                familyDeviceStatusDO.setBeginTime(LocalDateTime.now());
                familyDeviceStatusDO.setEndTime(LocalDateTime.now());
                save(familyDeviceStatusDO);
            }

        }
    }

    @Autowired
    public void setHomeAutoProductService(IHomeAutoProductService homeAutoProductService) {
        this.homeAutoProductService = homeAutoProductService;
    }

    @Autowired
    public void setRedisServiceForDeviceStatus(RedisServiceForDeviceStatus redisServiceForDeviceStatus) {
        this.redisServiceForDeviceStatus = redisServiceForDeviceStatus;
    }

}
