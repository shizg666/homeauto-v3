package com.landleaf.homeauto.center.device.cache;

import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.service.ITemplateFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName ConfigCacheProvider
 * @Description: 所有配置缓存提供类
 * @Author wyl
 * @Date 2021/4/2
 * @Version V1.0
 **/
@Component
public class FamilyCacheProvider extends BaseCacheProvider {

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;
    @Autowired
    private ITemplateFloorService templateFloorService;

    public List<FamilySceneVO> getCommonScenesByFamilyId4VO(Long familyId, Long templateId) {
        List<FamilySceneVO> result = null;
        String key = String.format(RedisCacheConst.FAMILY_COMMON_SCENE_CACHE,templateId, familyId);
        Object boFromRedis = getBoFromRedis(key, LIST_TYPE, FamilySceneVO.class);
        if (boFromRedis != null) {
            return (List<FamilySceneVO>) boFromRedis;
        }

        result = familyCommonSceneService.getCommonScenesByFamilyId4VO(familyId, templateId);
        if(!CollectionUtils.isEmpty(result)){
            redisUtils.set(key, result, RedisCacheConst.FAMILY_DEVICE_STATUS_EXPIRE);
        }
        return result;


    }

    public List<FloorRoomVO> getFloorAndRoomDevices(Long templateId) {

        List<FloorRoomVO> result = null;
        String key = String.format(RedisCacheConst.TEMPLATE_FLOOR_ROOM_DEVICE_CACHE,templateId);
        Object boFromRedis = getBoFromRedis(key, LIST_TYPE, FloorRoomVO.class);
        if (boFromRedis != null) {
            return (List<FloorRoomVO>) boFromRedis;
        }
        result = templateFloorService.getFloorAndRoomDevices(templateId);
        if(!CollectionUtils.isEmpty(result)){
            redisUtils.set(key, result, RedisCacheConst.CONFIG_COMMON_EXPIRE);
        }
        return result;

    }
}
