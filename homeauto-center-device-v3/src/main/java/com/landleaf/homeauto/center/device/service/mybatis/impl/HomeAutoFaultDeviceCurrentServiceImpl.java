package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultDeviceCurrentMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceCurrentService;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<HomeAutoFaultDeviceCurrent> getCurrentByDevice(Long familyId, Long deviceId) {
        QueryWrapper<HomeAutoFaultDeviceCurrent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id",deviceId);
        queryWrapper.eq("family_id",familyId);
        queryWrapper.orderByDesc("update_time");
        return list(queryWrapper);
    }

    @Override
    public void storeOrUpdateCurrentFaultValue(HomeAutoFaultDeviceCurrent data) {
        try {
            HomeAutoFaultDeviceCurrent exist=this.baseMapper.selectForUpdate(data.getFamilyId(),data.getDeviceId(),data.getType(),data.getCode());
            if(exist==null){
              save(data);
            }else {
                UpdateWrapper<HomeAutoFaultDeviceCurrent> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",exist.getId());
                updateWrapper.set("value",data.getValue());
                update(updateWrapper);
            }
        } catch (BeansException e) {
            log.error("新增或修改当前故障值异常:{}",e.getMessage());
        }
    }

    @Override
    public void removeCurrentFaultValue(Long familyId, Long deviceId, String code, int type) {
        UpdateWrapper<HomeAutoFaultDeviceCurrent> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("device_id",deviceId);
        updateWrapper.eq("family_id",familyId);
        updateWrapper.eq("code",code);
        updateWrapper.eq("type",type);
        remove(updateWrapper);
    }

    @Override
    public long countCurrentFault(Long familyId, Long deviceId,  int type) {
        QueryWrapper<HomeAutoFaultDeviceCurrent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id",deviceId);
        queryWrapper.eq("family_id",familyId);
        queryWrapper.eq("type",type);
        return count(queryWrapper);
    }
}
