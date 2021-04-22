package com.landleaf.homeauto.center.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceStatusMapper;
import com.landleaf.homeauto.center.data.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusService;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class FamilyDeviceStatusServiceImpl extends ServiceImpl<FamilyDeviceStatusMapper, FamilyDeviceStatusDO> implements IFamilyDeviceStatusService {

    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    @Override
    public void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList) {
        log.info("insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList):{} ", deviceStatusBOList.toString());
        for (DeviceStatusBO deviceStatusBO : deviceStatusBOList) {
            log.info("进入循环,deviceStatusBO的值为:{}", deviceStatusBO);
            String familyCode = deviceStatusBO.getFamilyCode();
            String productCode = deviceStatusBO.getProductCode();
            String deviceSn = deviceStatusBO.getDeviceSn();
            String statusCode = deviceStatusBO.getStatusCode();
            String statusValue = deviceStatusBO.getStatusValue();
            String key = RedisKeyUtils.getDeviceStatusKey(familyCode, deviceSn,  statusCode);
            Object deviceStatus = redisServiceForDeviceStatus.getDeviceStatus(key);
            log.info("上一次的状态为:{}", deviceStatus);
            if (!Objects.isNull(deviceStatus) && Objects.equals(deviceStatus.toString(), statusValue)) {
                // 如果设备的上次状态和上报状态一致,则更新状态的结束时间
                log.info("当前状态与上一次的状态一致,更新状态的结束时间");
                UpdateWrapper<FamilyDeviceStatusDO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("end_time", LocalDateTime.now());
                updateWrapper.eq("family_id", deviceStatusBO.getFamilyId());
                updateWrapper.eq("product_code", productCode);
                updateWrapper.eq("device_sn", deviceSn);
                updateWrapper.eq("status_code", statusCode);
                update(updateWrapper);
            } else {
                // 如果设备的上次状态和上报状态不一致,则插入一条新的状态
                log.info("当前状态与上一次状态不一致,插入一条新的状态");
                FamilyDeviceStatusDO familyDeviceStatusDO = new FamilyDeviceStatusDO();
                familyDeviceStatusDO.setStatusCode(statusCode);
                familyDeviceStatusDO.setDeviceSn(deviceSn);
                familyDeviceStatusDO.setStatusValue(statusValue);
                familyDeviceStatusDO.setFamilyId(deviceStatusBO.getFamilyId());
                familyDeviceStatusDO.setProductCode(productCode);
                familyDeviceStatusDO.setCategoryCode(deviceStatusBO.getCategoryCode());
                familyDeviceStatusDO.setBeginTime(LocalDateTime.now());
                familyDeviceStatusDO.setEndTime(LocalDateTime.now());
                familyDeviceStatusDO.setProjectId(deviceStatusBO.getProjectId());
                save(familyDeviceStatusDO);
            }

        }
    }



}
