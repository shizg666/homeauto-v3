package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constant.RedisCacheConst.KEY_USER_INFO;

/**
 * 后台用户信息缓存
 **/
@Service
public class UserInfoCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 获取后台账号信息
     *
     * @param userId 用户ID
     */
    public SysUser getUserInfo(String userId) {
        boolean hasKey = redisUtils.hasKey(KEY_USER_INFO);
        if (hasKey) {
            Object hget = redisUtils.hget(KEY_USER_INFO, userId);
            if (hget == null) {
                return getSysUserByDB(userId);
            }
            return JSON.parseObject(JSON.toJSONString(hget), SysUser.class);
        }
        return getSysUserByDB(userId);
    }

    private SysUser getSysUserByDB(String userId) {
        SysUser result = null;
        SysUser sysUser = sysUserService.getById(userId);
        if (sysUser != null) {
            result = new SysUser();
            BeanUtils.copyProperties(sysUser, result);
            redisUtils.hset(KEY_USER_INFO, userId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存所有用户记录
     */
    public void cacheAllUser() {
        List<SysUser> allUseres = sysUserService.list();
        redisUtils.del(KEY_USER_INFO);
        if (!CollectionUtils.isEmpty(allUseres)) {
            Map<String, SysUser> sysUserMap = allUseres.stream().collect(Collectors.toMap(SysUser::getId, i -> {
                return i;
            }, (o, n) -> o));
            redisUtils.addMapList(KEY_USER_INFO, sysUserMap);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllUser();
    }

    public void remove(String userId) {
        redisUtils.hdel(KEY_USER_INFO, userId);
    }
}
