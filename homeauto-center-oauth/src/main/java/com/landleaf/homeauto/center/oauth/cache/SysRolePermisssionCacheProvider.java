package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermission;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constance.RedisCacheConst.KEY_ROLE_PERMISSION;

/**
 *
 * 后台用户角色权限缓存
 **/
@Service
public class SysRolePermisssionCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    /**
     * 获取角色拥有权限
     *
     * @param roleId 角色ID
     * @return java.lang.String
     */
    public List<String> getRolePermisssions(String roleId) {
        boolean hasKey = redisUtils.hasKey(KEY_ROLE_PERMISSION);
        if (hasKey) {
            Object hget = redisUtils.hget(KEY_ROLE_PERMISSION, roleId);
            if (hget == null) {
                return getSysRolePermissionByDB(roleId);
            }
            return JSON.parseArray(JSON.toJSONString(hget), String.class);
        }
        return getSysRolePermissionByDB(roleId);
    }

    private List<String> getSysRolePermissionByDB(String roleId) {
        List<String> result = null;
        List<SysRolePermission> rolePermissions = sysRolePermissionService.getRolePermissionByRole(roleId);
        if (!CollectionUtils.isEmpty(rolePermissions)) {
            result = Lists.newArrayList();
            result.addAll(rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList()));
            redisUtils.hset(KEY_ROLE_PERMISSION, roleId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存角色所有权限
     */
    public void cacheAllRolePermisssion() {
        List<SysRolePermission> allRolePermission = sysRolePermissionService.list();
        redisUtils.del(KEY_ROLE_PERMISSION);
        if (!CollectionUtils.isEmpty(allRolePermission)) {
            Map<String, List<SysRolePermission>> tmpMap = allRolePermission.stream().collect(Collectors.groupingBy(SysRolePermission::getRoleId));
            Map<String, Object> data = Maps.newHashMap();
            for (Map.Entry<String, List<SysRolePermission>> entry : tmpMap.entrySet()) {
                data.put(entry.getKey(), entry.getValue().stream().map(i -> {
                    return i.getPermissionId();
                }).collect(Collectors.toList()));
            }
            redisUtils.hmset(KEY_ROLE_PERMISSION, data);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllRolePermisssion();
    }

    public void remove(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            redisUtils.del(KEY_ROLE_PERMISSION);
        } else {
            redisUtils.hdel(KEY_ROLE_PERMISSION, roleId);
        }

    }
}
