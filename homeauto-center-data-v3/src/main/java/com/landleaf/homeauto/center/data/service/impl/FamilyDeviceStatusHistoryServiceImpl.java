package com.landleaf.homeauto.center.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceStatusHistoryMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class FamilyDeviceStatusHistoryServiceImpl extends ServiceImpl<FamilyDeviceStatusHistoryMapper, FamilyDeviceStatusHistory> implements IFamilyDeviceStatusHistoryService {


    @Override
    public void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList, LocalDateTime now) {
        log.info("insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList):{} ", deviceStatusBOList.toString());
        for (DeviceStatusBO deviceStatusBO : deviceStatusBOList) {
            log.info("进入循环,deviceStatusBO的值为:{}", deviceStatusBO);
            String familyCode = deviceStatusBO.getFamilyCode();
            String productCode = deviceStatusBO.getProductCode();
            String deviceSn = deviceStatusBO.getDeviceSn();
            String statusCode = deviceStatusBO.getStatusCode();
            String statusValue = deviceStatusBO.getStatusValue();

            FamilyDeviceStatusHistory familyDeviceStatusDO = new FamilyDeviceStatusHistory();
            familyDeviceStatusDO.setStatusCode(statusCode);
            familyDeviceStatusDO.setDeviceSn(deviceSn);
            familyDeviceStatusDO.setStatusValue(statusValue);
            familyDeviceStatusDO.setFamilyId(deviceStatusBO.getFamilyId());
            familyDeviceStatusDO.setProductCode(productCode);
            familyDeviceStatusDO.setCategoryCode(deviceStatusBO.getCategoryCode());
            familyDeviceStatusDO.setProjectId(deviceStatusBO.getProjectId());
            familyDeviceStatusDO.setRealestateId(deviceStatusBO.getRealestateId());
            familyDeviceStatusDO.setUploadTime(now);
            save(familyDeviceStatusDO);
        }
    }
}
