package com.landleaf.homeauto.center.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusCurrent;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceStatusCurrentMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusCurrentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备状态表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Service
@Slf4j
public class FamilyDeviceStatusCurrentServiceImpl extends ServiceImpl<FamilyDeviceStatusCurrentMapper, FamilyDeviceStatusCurrent> implements IFamilyDeviceStatusCurrentService {

    @Override
    public void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList) {
        for (DeviceStatusBO deviceStatusBO : deviceStatusBOList) {

            String familyCode = deviceStatusBO.getFamilyCode();
            String productCode = deviceStatusBO.getProductCode();
            String deviceSn = deviceStatusBO.getDeviceSn();
            String statusCode = deviceStatusBO.getStatusCode();
            String statusValue = deviceStatusBO.getStatusValue();
            Long familyId = deviceStatusBO.getFamilyId();
            UpdateWrapper<FamilyDeviceStatusCurrent> removeWrapper = new UpdateWrapper<>();
            removeWrapper.eq("familyId",familyId);
            removeWrapper.eq("device_sn",deviceSn);
            removeWrapper.eq("status_code",statusCode);
            remove(removeWrapper);

            FamilyDeviceStatusCurrent familyDeviceStatusDO = new FamilyDeviceStatusCurrent();
            familyDeviceStatusDO.setStatusCode(statusCode);
            familyDeviceStatusDO.setDeviceSn(deviceSn);
            familyDeviceStatusDO.setStatusValue(statusValue);
            familyDeviceStatusDO.setFamilyId(deviceStatusBO.getFamilyId());
            familyDeviceStatusDO.setProductCode(productCode);
            familyDeviceStatusDO.setCategoryCode(deviceStatusBO.getCategoryCode());
            familyDeviceStatusDO.setProjectId(deviceStatusBO.getProjectId());
            familyDeviceStatusDO.setRealestateId(deviceStatusBO.getRealestateId());
            save(familyDeviceStatusDO);
        }
    }
}
