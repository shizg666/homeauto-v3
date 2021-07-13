package com.landleaf.homeauto.center.device.cache;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author Lokiy
 * @Date 2021/7/13 14:26
 * @Description 配置更新缓存处理提供类
 */
@Component
public class ChangeCacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 根据变动删除对应的缓存
     * @param mac 网关mac地址
     */
    public void changeMacCache(String mac){
        if(StrUtil.isBlank(mac)) {
            return;
        }
        //mac绑定家庭键   配置家庭mac键
        String[] keys = {
                String.format(RedisCacheConst.MAC_FAMILY,mac),
                String.format(RedisCacheConst.CONFIG_FAMILY_MAC_CACHE, mac)
        };
        redisUtils.del(keys);
    }


    /**
     * 根据家庭变更，更新相应缓存
     * @param familyId 家庭id
     */
    public void changeFamilyCache(Long familyId){
        if(Objects.isNull(familyId)){
            return;
        }
        //家庭id配置键
        String[] keys = {
                String.format(RedisCacheConst.CONFIG_FAMILY_CACHE, familyId)
        };
        //家庭设备配置键
        String familyDeviceKeys = String.format(RedisCacheConst.FAMILY_DEVICE_INFO_STATUS_CACHE, familyId, "*");
        Set<String> familyDeviceKeySet = redisUtils.keys(familyDeviceKeys);
        Collections.addAll(familyDeviceKeySet, keys);
        redisUtils.del(familyDeviceKeySet.toArray(new String[0]));
    }


    /**
     * 变更模板删除模板缓存
     * @param templateId 模板缓存id
     */
    public void changeTemplateCache(Long templateId){
        if(Objects.isNull(templateId)){
            return;
        }
        String[] keys = {
                String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE_PRE, templateId),
                String.format(RedisCacheConst.CONFIG_SYSTEM_PRODUCT_RELATED_CACHE, templateId),
                String.format(RedisCacheConst.TEMPLATE_FLOOR_ROOM_DEVICE_CACHE, templateId)
        };
        List<String> keyList = Lists.newArrayList();
        //模板家庭配置键
        String templateFamilySceneKeys = String.format(RedisCacheConst.FAMILY_COMMON_SCENE_CACHE, templateId, "*");
        Set<String> templateFamilySceneKeySet = redisUtils.keys(templateFamilySceneKeys);
        keyList.addAll(templateFamilySceneKeySet);

        //模板家庭配置键
        String configHouseTemplateAttrKeys = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_ATTR_CACHE, templateId, "*");
        Set<String> configHouseTemplateAttrKeySet = redisUtils.keys(configHouseTemplateAttrKeys);
        keyList.addAll(configHouseTemplateAttrKeySet);


        //模板设备缓存
        String configHouseTemplateDeviceKeys = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_DEVICE, templateId, "*", "*");
        String substring = configHouseTemplateDeviceKeys.substring(0, configHouseTemplateDeviceKeys.length() - 3);
        Set<String> configHouseTemplateDeviceKeySet = redisUtils.keys(substring);
        keyList.addAll(configHouseTemplateDeviceKeySet);

        Collections.addAll(keyList, keys);
        redisUtils.del(keyList.toArray(new String[0]));
    }
}
