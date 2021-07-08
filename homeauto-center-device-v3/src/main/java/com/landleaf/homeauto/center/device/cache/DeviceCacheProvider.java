package com.landleaf.homeauto.center.device.cache;

import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceInfoStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceCurrentService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceFaultCurrentDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ConfigCacheProvider
 * @Description: 所有配置缓存提供类
 * @Author wyl
 * @Date 2021/4/2
 * @Version V1.0
 **/
@Component
public class DeviceCacheProvider extends BaseCacheProvider {

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    private IFamilyDeviceInfoStatusService familyDeviceInfoStatusService;
    @Autowired
    private IHomeAutoFaultDeviceCurrentService faultDeviceCurrentService;

    /**
     * @param: deviceId
     * @description: 获取设备状态信息
     * @return: com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO
     * @author: wyl
     * @date: 2021/6/1
     */
    public ScreenDeviceInfoStatusDTO getFamilyDeviceInfoStatus(Long familyId, Long deviceId) {
        ScreenDeviceInfoStatusDTO result = null;
        String key = String.format(RedisCacheConst.FAMILY_DEVICE_INFO_STATUS_CACHE,familyId, deviceId);
        Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, ScreenDeviceInfoStatusDTO.class);
        if (boFromRedis != null) {
            return (ScreenDeviceInfoStatusDTO) boFromRedis;
        }
        FamilyDeviceInfoStatus familyDeviceInfoStatus=familyDeviceInfoStatusService.getFamilyDeviceInfoStatus(familyId,deviceId);
        if(!Objects.isNull(familyDeviceInfoStatus)){
            result=new ScreenDeviceInfoStatusDTO();
            List<HomeAutoFaultDeviceCurrent> currentFaults = faultDeviceCurrentService.getCurrentByDevice(familyId,deviceId);
            BeanUtils.copyProperties(familyDeviceInfoStatus,result);
            if(!CollectionUtils.isEmpty(currentFaults)){
                Map<Integer, Map<String, String>> currentDetailMap = currentFaults.stream().collect(Collectors.groupingBy(HomeAutoFaultDeviceCurrent::getType,
                        Collectors.toMap(HomeAutoFaultDeviceCurrent::getCode, HomeAutoFaultDeviceCurrent::getValue)));
                result.setCurrentDetailMap(currentDetailMap);
            }
            redisUtils.set(key, result, RedisCacheConst.FAMILY_DEVICE_STATUS_EXPIRE);
        }
        return result;

    }



    /**
     * 删除家庭设备状态信息缓存
     * @param familyId 家庭id
     */
    public void delFamilyDeviceInfoStatusCache(Long familyId){
        //查找同一家庭下所有设备的key
        String key = String.format(RedisCacheConst.FAMILY_DEVICE_INFO_STATUS_CACHE,familyId, "*");
        Set<String> keys = redisUtils.keys(key);
        redisUtils.del(keys.toArray(new String[0]));
    }
}
