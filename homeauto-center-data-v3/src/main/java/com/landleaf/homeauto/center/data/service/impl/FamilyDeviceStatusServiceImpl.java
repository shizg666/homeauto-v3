package com.landleaf.homeauto.center.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceStatusMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class FamilyDeviceStatusServiceImpl extends ServiceImpl<FamilyDeviceStatusMapper, FamilyDeviceStatusDO> implements IFamilyDeviceStatusService {


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

            UpdateWrapper<FamilyDeviceStatusDO> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("end_time", LocalDateTime.now());
            updateWrapper.eq("family_id", deviceStatusBO.getFamilyId());
            updateWrapper.eq("product_code", productCode);
            updateWrapper.eq("device_sn", deviceSn);
            updateWrapper.eq("status_code", statusCode);
            updateWrapper.eq("status_value", statusValue);
            boolean update = update(updateWrapper);
            if(!update){
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
                familyDeviceStatusDO.setRealestateId(deviceStatusBO.getRealestateId());
                save(familyDeviceStatusDO);
            }

        }
    }



}
