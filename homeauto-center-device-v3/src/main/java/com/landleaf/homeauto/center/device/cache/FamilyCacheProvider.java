package com.landleaf.homeauto.center.device.cache;

import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceInfoStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceCurrentService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

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
}
