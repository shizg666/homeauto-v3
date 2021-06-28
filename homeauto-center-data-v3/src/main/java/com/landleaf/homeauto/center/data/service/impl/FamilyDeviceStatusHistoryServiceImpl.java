package com.landleaf.homeauto.center.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceStatusHistoryMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Override
    public BasePageVO<FamilyDeviceStatusHistory> getStatusByFamily(HistoryQryDTO2 historyQryDTO) {

        BasePageVO<FamilyDeviceStatusHistory> basePageVO = new BasePageVO<>();

        PageHelper.startPage(historyQryDTO.getPageNum(),historyQryDTO.getPageSize(),true);

        LambdaQueryWrapper<FamilyDeviceStatusHistory> queryWrapper = new LambdaQueryWrapper<>();

        Long familyId = historyQryDTO.getFamilyId();
        String sn = historyQryDTO.getDeviceSn();

        if (StringUtils.isNotBlank(historyQryDTO.getCode())){
            queryWrapper.eq(FamilyDeviceStatusHistory::getStatusCode ,historyQryDTO.getCode());
        }

        if (StringUtils.isNotBlank(sn)){
            queryWrapper.eq(FamilyDeviceStatusHistory::getDeviceSn ,sn);
        }
        if (familyId > 0){
            queryWrapper.eq(FamilyDeviceStatusHistory::getFamilyId,familyId);
        }

        List<String> uploadTimes = historyQryDTO.getUploadTimes();

        String startTime = "";
        String endTime = "";


        if (!CollectionUtils.isEmpty(uploadTimes) && uploadTimes.size() ==2){
            startTime = uploadTimes.get(0);
            endTime = uploadTimes.get(1);
        }

        if (StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
            queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");
        }

        queryWrapper.orderByDesc(FamilyDeviceStatusHistory::getUploadTime);
        List<FamilyDeviceStatusHistory> result = list(queryWrapper);


        PageInfo pageInfo = new PageInfo(result);

        pageInfo.setList(result);

        basePageVO = BeanUtil.mapperBean(pageInfo, BasePageVO.class);


        return basePageVO;
    }
}
