package com.landleaf.homeauto.center.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.data.domain.FamilyDevicePowerHistory;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.domain.bo.FamilyDevicePowerDO;
import com.landleaf.homeauto.center.data.mapper.FamilyDevicePowerHistoryMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDevicePowerHistoryService;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备功耗表 服务实现类
 * </p>
 *
 */
@Service
@Slf4j
public class FamilyDevicePowerHistoryServiceImpl extends ServiceImpl<FamilyDevicePowerHistoryMapper, FamilyDevicePowerHistory> implements IFamilyDevicePowerHistoryService {


    @Override
    public void insertBatchDevicePower(List<FamilyDevicePowerDO> powerDOS) {
        log.info("insertBatchDevicePower(List<FamilyDevicePowerDO> powerDOS:{} ", powerDOS.toString());
        if(powerDOS.size()>0) {
            powerDOS.stream().forEach(s -> {
                FamilyDevicePowerHistory powerHistory = new FamilyDevicePowerHistory();
                BeanUtils.copyProperties(s, powerHistory);
                
                save(powerHistory);

            });
        }

    }

//    @Override
//    public BasePageVO<FamilyDeviceStatusHistory> getStatusByFamily(HistoryQryDTO2 historyQryDTO) {
//
//        BasePageVO<FamilyDeviceStatusHistory> basePageVO = new BasePageVO<>();
//
//        PageHelper.startPage(historyQryDTO.getPageNum(),historyQryDTO.getPageSize(),true);
//
//        LambdaQueryWrapper<FamilyDeviceStatusHistory> queryWrapper = new LambdaQueryWrapper<>();
//
//        Long familyId = historyQryDTO.getFamilyId();
//        String sn = historyQryDTO.getDeviceSn();
//
//        if (StringUtils.isNotBlank(historyQryDTO.getCode())){
//            queryWrapper.eq(FamilyDeviceStatusHistory::getStatusCode ,historyQryDTO.getCode());
//        }
//
//        if (StringUtils.isNotBlank(sn)){
//            queryWrapper.eq(FamilyDeviceStatusHistory::getDeviceSn ,sn);
//        }
//        if (familyId > 0){
//            queryWrapper.eq(FamilyDeviceStatusHistory::getFamilyId,familyId);
//        }
//
//        List<String> uploadTimes = historyQryDTO.getUploadTimes();
//
//        String startTime = "";
//        String endTime = "";
//
//
//        if (!CollectionUtils.isEmpty(uploadTimes) && uploadTimes.size() ==2){
//            startTime = uploadTimes.get(0);
//            endTime = uploadTimes.get(1);
//        }
//
//        if (StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
//            queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");
//        }
//
//        queryWrapper.orderByDesc(FamilyDeviceStatusHistory::getUploadTime);
//        List<FamilyDeviceStatusHistory> result = list(queryWrapper);
//
//
//        PageInfo pageInfo = new PageInfo(result);
//
//        pageInfo.setList(result);
//
//        basePageVO = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
//
//
//        return basePageVO;
//    }
}
