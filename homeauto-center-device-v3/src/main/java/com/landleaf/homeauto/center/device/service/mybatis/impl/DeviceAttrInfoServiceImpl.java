package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.mapper.DeviceAttrInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrBitService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrPrecisionService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrSelectService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 协议属性信息 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Slf4j
@Service
public class DeviceAttrInfoServiceImpl extends ServiceImpl<DeviceAttrInfoMapper, DeviceAttrInfo> implements IDeviceAttrInfoService {

    @Autowired
    private IDeviceAttrBitService iDeviceAttrBitService;
    @Autowired
    private IDeviceAttrPrecisionService iDeviceAttrPrecisionService;
    @Autowired
    private IDeviceAttrSelectService iDeviceAttrSelectService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<DeviceAttrInfo> getAttributesByDeviceId(String deviceId, Integer type,Integer appFlag) {
        QueryWrapper<DeviceAttrInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", deviceId);
        if(type!=null){
            queryWrapper.eq("type", type);
        }
        if(appFlag!=null){
            queryWrapper.eq("app_flag", appFlag);
        }
        return list(queryWrapper);
    }

    @Override
    public AttributeErrorDTO getAttrError(AttributeErrorQryDTO request) {

        return null;
    }

    @Override
    public List<String> getAttrErrorCodeListByDeviceId(String deviceId) {
        return this.baseMapper.getAttrErrorCodeListByDeviceId(deviceId);
    }

    @Override
    public void errorAttrInfoCacheDelete(DeviceOperateEvent event) {
        List<String> errors = event.getErrorAttrCodes();
        if (CollectionUtils.isEmpty(errors)){
            return;
        }
        errors.forEach(error->{
            String caheKey = String.format(RedisCacheConst.DEVICE_ERROR_ATTR_INFO,
                    event.getDeviceId(), error);
//            log.error("****************************删除key:{}",caheKey);
            redisUtils.del(caheKey);
        });
    }

    @Override
    public DeviceAttrInfoCacheBO getAndSaveAttrInfoCache(String templateId, String attrCode) {
        return null;
    }




    @Override
    public void errorAttrInfoCacheAdd(DeviceOperateEvent event) {

    }
}
