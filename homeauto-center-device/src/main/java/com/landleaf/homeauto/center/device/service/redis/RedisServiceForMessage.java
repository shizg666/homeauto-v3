package com.landleaf.homeauto.center.device.service.redis;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.vo.area.AreaInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.JsonUtil;
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
        JSONObject jsonStr = (JSONObject) redisUtils.get(RedisCacheConst.AREA_INFO.concat(code));
        return JsonUtil.jsonToBean(jsonStr.toJSONString(),AreaInfo.class);
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
