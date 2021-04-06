package com.landleaf.homeauto.center.device.cache;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @ClassName ConfigCacheProvider
 * @Description: 所有配置缓存提供类
 * @Author wyl
 * @Date 2021/4/2
 * @Version V1.0
 **/
@Component
public class ConfigCacheProvider {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IHomeAutoFamilyService familyService;
    /**
     * 序列化类型-单体
     */
    private static final Integer SINGLE_TYPE = 1;
    /**
     * 序列化类型-集合
     */
    private static final Integer LIST_TYPE = 2;


    /**
     * 户型-设备缓存
     *
     * @param houseTemplateId
     * @param deviceId
     * @return
     */
    public ScreenTemplateDeviceBO getFamilyDevice(String houseTemplateId, String deviceSn, String deviceId) {
        String realKey = null;
        if (StringUtils.isEmpty(deviceId) && !StringUtils.isEmpty(deviceSn)) {
            throw new BusinessException("必須有一个");
        }
        String preKey = RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE_PRE;
        String key = String.format(preKey, houseTemplateId);
        Set<String> keys = redisUtils.keys(key);
        if (!CollectionUtils.isEmpty(keys)) {
            Optional<String> first = keys.stream().filter(i -> {
                boolean flag = true;
                String[] target = i.split(CommonConst.SymbolConst.COLON);
                if (!StringUtils.isEmpty(deviceSn)) {
                    if (!StringUtils.equals(deviceSn, target[3])) {
                        flag = false;
                    }
                }
                if (!StringUtils.isEmpty(deviceId)) {
                    if (!StringUtils.equals(deviceId, target[4])) {
                        flag = false;
                    }
                }
                return flag;
            }).findFirst();
            if (first.isPresent()) {
                realKey = first.get();
                Object boFromRedis = getBoFromRedis(realKey, SINGLE_TYPE, ScreenTemplateDeviceBO.class);
                if (boFromRedis != null) {
                    return (ScreenTemplateDeviceBO) boFromRedis;
                }

            }
        }
        // TODO 从数据库获取相应信息
        realKey = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE, houseTemplateId, deviceSn, deviceId);
        ScreenTemplateDeviceBO result = new ScreenTemplateDeviceBO();
        redisUtils.set(realKey, result, RedisCacheConst.CONFIG_COMMON_EXPIRE);
        return result;
    }

    /**
     * 户型-设备缓存
     *
     * @param houseTemplateId
     * @param deviceSn
     * @return
     */
    public ScreenTemplateDeviceBO getFamilyDeviceBySn(String houseTemplateId, String deviceSn) {
        return getFamilyDevice(houseTemplateId, deviceSn, null);
    }

    /**
     * 户型-设备缓存
     *
     * @param houseTemplateId
     * @return
     */
    public ScreenTemplateDeviceBO getFamilyDeviceByDeviceId(String houseTemplateId, String deviceId) {
        return getFamilyDevice(houseTemplateId, null, deviceId);
    }


    /**
     * 产品属性缓存
     *
     * @param productCode
     * @return java.util.List<com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO>
     * @author wenyilu
     * @date 2021/4/2 11:19
     */
    public List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode) {
        String key = String.format(RedisCacheConst.CONFIG_PRODUCT_ATTR_CACHE, productCode);
        Object boFromRedis = getBoFromRedis(key, LIST_TYPE, ScreenProductAttrCategoryBO.class);
        if (boFromRedis != null) {
            return (List<ScreenProductAttrCategoryBO>) boFromRedis;
        }
        // TODO 从数据库获取相应信息
        List<ScreenProductAttrCategoryBO> result = Lists.newArrayList();
        redisUtils.set(key, result, RedisCacheConst.PRODUCT_ATTR_CACHE_EXPIRE);
        return result;
    }

    Object getBoFromRedis(String key, Integer type, Class classz) {
        if (redisUtils.hasKey(key)) {
            Object o = redisUtils.get(key);
            if (o != null) {
                if (type == 1) {
                    return JSON.parseObject(JSON.toJSONString(o), classz);
                }
                return JSON.parseArray(JSON.toJSONString(o), classz);
            }
        }
        return null;
    }

    /**
     * 获取家庭信息
     *
     * @param familyId
     * @return com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO
     * @author wenyilu
     * @date 2021/4/2 11:24
     */
    public ScreenFamilyBO getFamilyInfo(String familyId) {
        String key = String.format(RedisCacheConst.CONFIG_FAMILY_CACHE, familyId);
        Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, ScreenFamilyBO.class);
        if (boFromRedis != null) {
            return (ScreenFamilyBO) boFromRedis;
        }
        // TODO 从数据库获取相应信息
        ScreenFamilyBO result = new ScreenFamilyBO();
        redisUtils.set(key, result, RedisCacheConst.CONFIG_COMMON_EXPIRE);
        return result;

    }

    /**
     * 獲取家庭信息
     *
     * @param mac
     * @return
     */
    public ScreenFamilyBO getFamilyInfoByMac(String mac) {
        String key = String.format(RedisCacheConst.CONFIG_FAMILY_MAC_CACHE, mac);
        Object boFromRedis = getBoFromRedis(key, SINGLE_TYPE, String.class);
        String familyId = null;
        if (boFromRedis != null) {
            familyId = (String) boFromRedis;
        }
        FamilyInfoBO familyInfoByTerminalMac = familyService.getFamilyInfoByTerminalMac(mac);
        if (familyInfoByTerminalMac != null) {
            redisUtils.set(key, familyInfoByTerminalMac.getFamilyId(), RedisCacheConst.CONFIG_COMMON_EXPIRE);
        }
        if (!StringUtils.isEmpty(familyId)) {
            return getFamilyInfo(familyId);
        }
        return null;

    }
}
