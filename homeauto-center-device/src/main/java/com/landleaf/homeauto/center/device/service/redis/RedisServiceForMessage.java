package com.landleaf.homeauto.center.device.service.redis;

import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.domain.vo.area.AreaInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息工具类
 *
 * @author wenyilu
 */
@Service
public class RedisServiceForMessage {

    private RedisUtils redisUtils;

    /**
     * 获取存入的地址信息
     *
     * @param code
     * @return
     */
    public AreaInfo getAreaInfoByCode(String code) {
        return (AreaInfo) redisUtils.hget(RedisCacheConst.KEY_AREA_INFO, code);
    }

    /**
     * 获取中文地址路径
     *
     * @param code
     * @return
     */
    public String getAreaInfoPathName(String code) {
        AreaInfo areaInfo = getAreaInfoByCode(code);
        if (areaInfo == null) {
            return null;
        }
        return areaInfo.getPathName();
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
}
