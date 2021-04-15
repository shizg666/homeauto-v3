package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionScopService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermissionScop;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constant.RedisCacheConst.KEY_USER_PERMISSION_SCOP;

/**
 * 后台用户拥有权限范围缓存
 **/
@Service
public class SysPermissionScopCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysRolePermissionScopService sysRolePermissionScopService;

    /**
     * 获取角色权限范围
     *
     * @param roleId 角色ID
     * @return
     */
    public List<String> getPermisssionScopPaths(String roleId) {
        boolean hasKey = redisUtils.hasKey(KEY_USER_PERMISSION_SCOP);
        if (hasKey) {
            Object hget = redisUtils.hget(KEY_USER_PERMISSION_SCOP, roleId);
            if (hget == null) {
                return getPermisssionScopByDB(roleId);
            }
            return JSON.parseArray(JSON.toJSONString(hget), String.class);
        }
        return getPermisssionScopByDB(roleId);
    }

    private List<String> getPermisssionScopByDB(String roleId) {

        List<String> result = null;
        List<String> paths = sysRolePermissionScopService.queryPathsByRoleId(roleId);
        if (!CollectionUtils.isEmpty(paths)) {
            result = Lists.newArrayList();
            result.addAll(paths);
            redisUtils.hset(KEY_USER_PERMISSION_SCOP, roleId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存所有角色权限范围
     */
    public void cacheAllPermissionScop() {
        List<SysRolePermissionScop> allPermissionScop = sysRolePermissionScopService.list();
        redisUtils.del(KEY_USER_PERMISSION_SCOP);
        if (!CollectionUtils.isEmpty(allPermissionScop)) {
            Map<String, List<SysRolePermissionScop>> tmpMap = allPermissionScop.stream().collect(Collectors.groupingBy(SysRolePermissionScop::getRoleId));
            Map<String, Object> data = Maps.newHashMap();
            for (Map.Entry<String, List<SysRolePermissionScop>> entry : tmpMap.entrySet()) {
                data.put(entry.getKey(), entry.getValue().stream().map(i -> {
                    return i.getPath();
                }).collect(Collectors.toList()));
            }
            redisUtils.hmset(KEY_USER_PERMISSION_SCOP, data);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllPermissionScop();
    }


    public void remove(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            redisUtils.del(KEY_USER_PERMISSION_SCOP);
        } else {
            redisUtils.hdel(KEY_USER_PERMISSION_SCOP, roleId);
        }
    }
}
