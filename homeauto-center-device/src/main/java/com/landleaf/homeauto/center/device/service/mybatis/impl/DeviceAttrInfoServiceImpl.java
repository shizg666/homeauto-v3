package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrBit;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrPrecision;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrSelect;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrBit;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrPrecision;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrSelect;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.mapper.DeviceAttrInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrBitService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrPrecisionService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrSelectService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.enums.protocol.ProtocolAttrValTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        AttributeErrorDTO data = this.baseMapper.getAttrError(request);
        if (data == null){
            return data;
        }
        if (ProtocolAttrValTypeEnum.BIT.getCode().equals(data.getType())){
            List<DeviceAttrBit> deviceAttrBits = iDeviceAttrBitService.getListByAttrId(data.getId());
            if (CollectionUtils.isEmpty(deviceAttrBits)){
                return data;
            }
            List<Map<String,String>> dataMap = Lists.newArrayListWithCapacity(deviceAttrBits.size());
            deviceAttrBits.forEach(obj->{
                Map<String,String> map = Maps.newHashMapWithExpectedSize(2);
                map.put("0",obj.getBit0());
                map.put("1",obj.getBit1());
                dataMap.add(map);
            });
            data.setDesc(dataMap);
        }else if(ProtocolAttrValTypeEnum.VALUE.getCode().equals(data.getType())) {
            DeviceAttrPrecision deviceAttrPrecision = iDeviceAttrPrecisionService.getOne(new LambdaQueryWrapper<DeviceAttrPrecision>().eq(DeviceAttrPrecision::getAttrId,data.getId()).select(DeviceAttrPrecision::getMax,DeviceAttrPrecision::getMin));
            if (deviceAttrPrecision == null){
                return data;
            }
            data.setMax(deviceAttrPrecision.getMax());
            data.setMin(deviceAttrPrecision.getMin());
        }
        String caheKey = String.format(RedisCacheConst.DEVICE_ERROR_ATTR_INFO,
                request.getDeviceId(), request.getCode());
        Long ramdom = RandomUtils.nextLong(1,15);
        redisUtils.set(caheKey,data,ramdom*RedisCacheConst.HOUR_24_EXPIRE);
        return data;
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
        DeviceAttrInfoCacheBO cacheBO = this.baseMapper.getAndSaveAttrInfoCache(templateId,attrCode);
        if (Objects.isNull(cacheBO)){
            return null;
        }
        if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(cacheBO.getValueType())){
            DeviceAttrPrecision precision = iDeviceAttrPrecisionService.getByAttribute(cacheBO.getId());
            if (Objects.isNull(precision)){
                return cacheBO;
            }
            ProtocolAttrPrecision precisionData = BeanUtil.mapperBean(precision,ProtocolAttrPrecision.class);
            cacheBO.setProtocolAttrPrecision(precisionData);
        }else if (ProtocolAttrValTypeEnum.SELECT.getCode().equals(cacheBO.getValueType())){
            List<DeviceAttrSelect> selects = iDeviceAttrSelectService.getByAttribute(cacheBO.getId());
            if (CollectionUtils.isEmpty(selects)){
                return cacheBO;
            }
            List<ProtocolAttrSelect> attrSelects = BeanUtil.mapperList(selects,ProtocolAttrSelect.class);
            cacheBO.setProtocolAttrDetail(attrSelects);
        } else if (ProtocolAttrValTypeEnum.BIT.getCode().equals(cacheBO.getValueType())){
            List<DeviceAttrBit> attrBits = iDeviceAttrBitService.getListByAttrId(cacheBO.getId());
            if (CollectionUtils.isEmpty(attrBits)){
                return cacheBO;
            }
            List<ProtocolAttrBit> attrBitList = BeanUtil.mapperList(attrBits,ProtocolAttrBit.class);
            cacheBO.setProtocolAttrBitDTO(attrBitList);
        }
        redisUtils.set(String.format(RedisCacheConst.DEVICE_ATTR_INFO,
                templateId,attrCode), cacheBO);
        return cacheBO;
    }




    @Override
    public void errorAttrInfoCacheAdd(DeviceOperateEvent event) {
        List<AttributeErrorDTO> errorDTOS = this.baseMapper.getListAttrError(event.getDeviceId());
        if (CollectionUtils.isEmpty(errorDTOS)){
            return;
        }
        for (AttributeErrorDTO deviceAttr : errorDTOS) {
            String caheKey = String.format(RedisCacheConst.DEVICE_ERROR_ATTR_INFO,
                    event.getDeviceId(), deviceAttr.getCode());
            if (ProtocolAttrValTypeEnum.BIT.getCode().equals(deviceAttr.getType())) {
                List<DeviceAttrBit> deviceAttrBits = iDeviceAttrBitService.getListByAttrId(deviceAttr.getId());
                if (CollectionUtils.isEmpty(deviceAttrBits)) {
                    continue;
                }
                List<Map<String, String>> dataMap = Lists.newArrayListWithCapacity(deviceAttrBits.size());
                deviceAttrBits.forEach(obj -> {
                    Map<String, String> map = Maps.newHashMapWithExpectedSize(2);
                    map.put("0", obj.getBit0());
                    map.put("1", obj.getBit1());
                    dataMap.add(map);
                });
                deviceAttr.setDesc(dataMap);
            } else if (ProtocolAttrValTypeEnum.VALUE.getCode().equals(deviceAttr.getType())) {
                DeviceAttrPrecision deviceAttrPrecision = iDeviceAttrPrecisionService.getOne(new LambdaQueryWrapper<DeviceAttrPrecision>().eq(DeviceAttrPrecision::getAttrId, deviceAttr.getId()).select(DeviceAttrPrecision::getMax, DeviceAttrPrecision::getMin));
                if (deviceAttrPrecision == null) {
                    continue;
                }
                deviceAttr.setMax(deviceAttrPrecision.getMax());
                deviceAttr.setMin(deviceAttrPrecision.getMin());
            }
            Long ramdom = RandomUtils.nextLong(1,15);
            redisUtils.set(caheKey, deviceAttr, ramdom*RedisCacheConst.HOUR_24_EXPIRE);
        }
    }
}
