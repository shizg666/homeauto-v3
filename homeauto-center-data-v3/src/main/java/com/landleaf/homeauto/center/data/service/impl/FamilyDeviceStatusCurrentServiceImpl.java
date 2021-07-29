package com.landleaf.homeauto.center.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.data.domain.CurrentQryDTO;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusCurrent;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceStatusCurrentMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusCurrentService;
import com.landleaf.homeauto.common.domain.dto.datacollect.SyncCloudDTO;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class FamilyDeviceStatusCurrentServiceImpl extends ServiceImpl<FamilyDeviceStatusCurrentMapper, FamilyDeviceStatusCurrent> implements IFamilyDeviceStatusCurrentService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList, LocalDateTime now) {
        for (DeviceStatusBO deviceStatusBO : deviceStatusBOList) {

            String familyCode = deviceStatusBO.getFamilyCode();
            String productCode = deviceStatusBO.getProductCode();
            String deviceSn = deviceStatusBO.getDeviceSn();
            String statusCode = deviceStatusBO.getStatusCode();
            String statusValue = deviceStatusBO.getStatusValue();
            Long familyId = deviceStatusBO.getFamilyId();
            UpdateWrapper<FamilyDeviceStatusCurrent> removeWrapper = new UpdateWrapper<>();
            removeWrapper.eq("family_id",familyId);
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
            familyDeviceStatusDO.setUploadTime(now);
            save(familyDeviceStatusDO);
        }
    }

    @Override
    public FamilyDeviceStatusCurrent getStatusCurrent(CurrentQryDTO qryDTO) {

        FamilyDeviceStatusCurrent familyDeviceStatusCurrent = null;

        LambdaQueryWrapper<FamilyDeviceStatusCurrent> queryWrapper = new LambdaQueryWrapper<>();

        Long familyId = qryDTO.getFamilyId();
        String sn = qryDTO.getDeviceSn();

        if (StringUtils.isNotBlank(qryDTO.getCode())){
            queryWrapper.eq(FamilyDeviceStatusCurrent::getStatusCode ,qryDTO.getCode());
        }

        if (StringUtils.isNotBlank(sn)){
            queryWrapper.eq(FamilyDeviceStatusCurrent::getDeviceSn ,sn);
        }
        if (familyId > 0){
            queryWrapper.eq(FamilyDeviceStatusCurrent::getFamilyId,familyId);
        }

        queryWrapper.orderByDesc(FamilyDeviceStatusCurrent::getUploadTime);

        List<FamilyDeviceStatusCurrent> list = list(queryWrapper);

        if (list.size()>0){

            familyDeviceStatusCurrent = list.get(0);

        }

        return familyDeviceStatusCurrent;
    }

    @Override
    public void syncDeviceStatusCurrent(SyncCloudDTO data) {
//        if (StringUtil.isEmpty(syncCloudDTO.getEncodeData())){
//            return;
//        }
//        List<FamilyDeviceStatusHistory> dataDos = JSON.parseArray(data, FamilyDeviceStatusHistory.class);

    }
}
