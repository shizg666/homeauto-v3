package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.redis.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constance.RedisCacheConst.KEY_SYS_PERMISSION;

/**
 * 后台用户拥有权限缓存
 **/
@Service
public class SysPermisssionCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysPermissionService sysPermissionService;

    /**
     * 获取权限
     *
     * @param permisssionId 权限ID
     * @return
     */
    public SysPermission getSysUserPermissions(String permisssionId) {
        boolean hasKey = redisUtil.hasKey(KEY_SYS_PERMISSION);
        if (hasKey) {
            Object hget = redisUtil.hget(KEY_SYS_PERMISSION, permisssionId);
            if (hget == null) {
                return getSysPermissionsByDB(permisssionId);
            }
            return JSON.parseObject(JSON.toJSONString(hget), SysPermission.class);
        }
        return getSysPermissionsByDB(permisssionId);
    }


    private SysPermission getSysPermissionsByDB(String permisssionId) {
        SysPermission result = null;
        SysPermission permission = sysPermissionService.getById(permisssionId);
        if (permission != null) {
            result = new SysPermission();
            BeanUtils.copyProperties(permission, result);
            redisUtil.hset(KEY_SYS_PERMISSION, permisssionId, result, COMMON_EXPIRE);
        }
        return result;

    }

    /**
     * 缓存所有权限
     */
    public void cacheAllPermission() {
        List<SysPermission> allPermission = sysPermissionService.list();
        redisUtil.del(KEY_SYS_PERMISSION);
        if (!CollectionUtils.isEmpty(allPermission)) {
            Map<String, Object> permissionMap = allPermission.stream().collect(Collectors.toMap(SysPermission::getId, i -> {
                return i;
            }, (o, n) -> o));
            redisUtil.hmset(KEY_SYS_PERMISSION, permissionMap);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllPermission();
    }


    public void remove(String permissionId) {
        if (StringUtils.isEmpty(permissionId)) {
            redisUtil.del(KEY_SYS_PERMISSION);
        } else {
            redisUtil.hdel(KEY_SYS_PERMISSION, permissionId);
        }
    }
}
