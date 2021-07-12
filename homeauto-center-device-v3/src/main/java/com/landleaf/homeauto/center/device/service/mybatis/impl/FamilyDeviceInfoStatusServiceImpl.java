package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceInfoStatusMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceInfoStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备基本状态表(暖通、数值、在线离线标记) 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Slf4j
@Service
public class FamilyDeviceInfoStatusServiceImpl extends ServiceImpl<FamilyDeviceInfoStatusMapper, FamilyDeviceInfoStatus> implements IFamilyDeviceInfoStatusService {

    @Override
    public FamilyDeviceInfoStatus getFamilyDeviceInfoStatus(Long familyId, Long deviceId) {
        QueryWrapper<FamilyDeviceInfoStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id",deviceId);
        queryWrapper.eq("family_id",familyId);
        queryWrapper.orderByDesc("update_time");
        queryWrapper.last("limit 1 offset 0");
        return getOne(queryWrapper);
    }

    @Override
    public boolean storeOrUpdateDeviceInfoStatus(FamilyDeviceInfoStatus familyDeviceInfoStatus, int type) {

        try {
            FamilyDeviceInfoStatus exist = this.baseMapper.selectForUpdate(familyDeviceInfoStatus.getFamilyId(),
                    familyDeviceInfoStatus.getDeviceId());
            if(exist==null){
              save(familyDeviceInfoStatus);
              return true;
            }else {
                switch (type){
                    case 1:
                        exist.setHavcFaultFlag(familyDeviceInfoStatus.getHavcFaultFlag());
                        break;
                    case 2:
                        exist.setValueFaultFlag(familyDeviceInfoStatus.getValueFaultFlag());
                        break;
                    case 3:
                        exist.setOnlineFlag(familyDeviceInfoStatus.getOnlineFlag());
                        break;
                }
                 updateById(exist);
                return true;
            }
        } catch (BeansException e) {
            log.error("修改或新增设备自身状态信息异常:{}",e.getMessage());
        }
        return false;
    }

    @Override
    public void updateOnLineFlagByFamily(Long familyId, Integer status) {
        UpdateWrapper<FamilyDeviceInfoStatus> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("family_id",familyId);
        updateWrapper.set("online_flag",status);
        update(updateWrapper);
    }

    @Override
    public List<FamilyDeviceInfoStatus> getListStatistic(List<Long> familyIds) {
        List<FamilyDeviceInfoStatus> data = list(new LambdaQueryWrapper<FamilyDeviceInfoStatus>()
                .eq(FamilyDeviceInfoStatus::getHavcFaultFlag,1)
                .or()
                .eq(FamilyDeviceInfoStatus::getOnlineFlag,1)
                .in(FamilyDeviceInfoStatus::getFamilyId,familyIds)
                .select(FamilyDeviceInfoStatus::getCategoryCode,
                        FamilyDeviceInfoStatus::getDeviceId,
                        FamilyDeviceInfoStatus::getFamilyId,
                        FamilyDeviceInfoStatus::getHavcFaultFlag,
                        FamilyDeviceInfoStatus::getOnlineFlag));
        return data;
    }

    @Override
    public Map<String, Long> getOnlineCountMap() {
        List<FamilyDeviceInfoStatus> list = this.list();
        return list.stream().filter(t -> t.getOnlineFlag() == 1).collect(Collectors.groupingBy(FamilyDeviceInfoStatus::getCategoryCode, Collectors.counting()));
    }
}
