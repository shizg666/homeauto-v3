package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constance.RedisCacheConst.KEY_USER_ROLE;

/**
 * 用户关联角色缓存
 **/
@Service
public class SysUserRoleCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 获取用户角色关联记录
     *
     * @param userId 用户ID
     * @return com.landleaf.smarthome.domain.po.uc.SysUserRole
     */
    public SysUserRole getUserRole(String userId) {
        boolean hasKey = redisUtils.hasKey(KEY_USER_ROLE);
        if (hasKey) {
            Object hget = redisUtils.hget(KEY_USER_ROLE, userId);
            if (hget == null) {
                return getSysRoleByDB(userId);
            }
            return JSON.parseObject(JSON.toJSONString(hget), SysUserRole.class);
        }
        return getSysRoleByDB(userId);
    }

    private SysUserRole getSysRoleByDB(String userId) {
        SysUserRole result = null;
        SysUserRole sysUserRole = sysUserRoleService.getByUserAndRole(userId);
        if (sysUserRole != null) {
            result = new SysUserRole();
            BeanUtils.copyProperties(sysUserRole, result);
            redisUtils.hset(KEY_USER_ROLE, userId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存所有用户角色关联记录
     */
    public void cacheAllUserRole() {
        List<SysUserRole> allUserRole = sysUserRoleService.list();
        redisUtils.del(KEY_USER_ROLE);
        if (!CollectionUtils.isEmpty(allUserRole)) {
            Map<String, Object> roleMap = allUserRole.stream().collect(Collectors.toMap(SysUserRole::getUserId, i -> {
                return i;
            }, (o, n) -> o));
            redisUtils.hmset(KEY_USER_ROLE, roleMap);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllUserRole();
    }

    public void reomve(String userId) {
        if (StringUtils.isEmpty(userId)) {
            redisUtils.del(KEY_USER_ROLE);
        } else {
            redisUtils.hdel(KEY_USER_ROLE, userId);
        }
    }
}
