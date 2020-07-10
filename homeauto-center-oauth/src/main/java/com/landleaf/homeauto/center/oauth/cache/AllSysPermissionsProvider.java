package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.*;

/**
 * 所有权限缓存
 *
 * @author wenyilu
 */
@Service
public class AllSysPermissionsProvider implements CacheProvider {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysPermissionService sysPermissionService;

    public List<SysPermission> getAllSysPermissions(Integer permissionType) {
        String key = String.format(PERMISSION_BY_TYPE, permissionType);
        String cacheData = (String) redisUtil.get(key);
        if (org.springframework.util.StringUtils.isEmpty(cacheData)) {
            QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
            if (permissionType != null) {
                queryWrapper.eq("permission_type", permissionType);
            }
            List<SysPermission> allPermissions = sysPermissionService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(allPermissions)) {
                redisUtil.set(key, JSON.toJSONString(allPermissions), 60 * 30);
            }
            return allPermissions;
        }
        return JSON.parseArray(cacheData, SysPermission.class);

    }

    public void remove() {
        //清除所有的缓存
        try {
            Set<String> keys = redisUtil.keys(PERMISSION_BY_TYPE);
            for (String key : keys) {
                redisUtil.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
