package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultDeviceCurrentMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceCurrentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备当前故障值 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Service
@Slf4j
public class HomeAutoFaultDeviceCurrentServiceImpl extends ServiceImpl<HomeAutoFaultDeviceCurrentMapper, HomeAutoFaultDeviceCurrent> implements IHomeAutoFaultDeviceCurrentService {

    @Override
    public HomeAutoFaultDeviceCurrent getCurrentByDevice(Long familyId, Long deviceId) {
        QueryWrapper<HomeAutoFaultDeviceCurrent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id",deviceId);
        queryWrapper.eq("family_id",familyId);
        queryWrapper.orderByDesc("update_time");
        queryWrapper.last("limit 1 offset 0");
        return getOne(queryWrapper);
    }

    @Override
    public void storeOrUpdateCurrentFaultValue(HomeAutoFaultDeviceCurrent data, int type) {
        try {
            HomeAutoFaultDeviceCurrent exist=this.baseMapper.selectForUpdate(data.getFamilyId(),data.getDeviceId());
            if(exist==null){
              save(data);
            }else {
                switch (type){
                    case 1:
                        exist.setHavcErrorValue(data.getHavcErrorValue());
                        break;
                    case 2:
                        exist.setNumErrorValue(data.getNumErrorValue());
                        break;
                    case 3:
                        exist.setOnlineValue((data.getOnlineValue()));
                        break;
                }
                UpdateWrapper<HomeAutoFaultDeviceCurrent> updateWrapper = new UpdateWrapper<>();
                updateWrapper.setEntity(data);
                updateWrapper.eq("family_id",data.getFamilyId());
                updateWrapper.eq("device_id",data.getDeviceId());
                update(updateWrapper);
            }
        } catch (BeansException e) {
            log.error("新增或修改当前故障值异常:{}",e.getMessage());
        }
    }
}
