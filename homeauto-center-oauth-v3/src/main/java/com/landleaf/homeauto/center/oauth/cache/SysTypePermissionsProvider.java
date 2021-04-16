package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.*;

/**
 * 所有权限缓存（根据类型存储）
 *
 * @author wenyilu
 */
@Service
public class SysTypePermissionsProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysPermissionService sysPermissionService;

    public List<SysPermission> getAllSysPermissions(Integer permissionType) {
        String key = String.format(PERMISSION_BY_TYPE, permissionType);
        String cacheData = (String) redisUtils.get(key);
        if (org.springframework.util.StringUtils.isEmpty(cacheData)) {
            QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
            if (permissionType != null) {
                queryWrapper.eq("permission_type", permissionType);
            }
            List<SysPermission> permissionsByType = sysPermissionService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(permissionsByType)) {
                redisUtils.set(key, JSON.toJSONString(permissionsByType), 60 * 30);
            }
            return permissionsByType;
        }
        return JSON.parseArray(cacheData, SysPermission.class);

    }

    @Override
    public void remove(String permissionType) {
        if(StringUtils.isEmpty(permissionType)){
            remove();
            return;
        }
        try {
            Set<String> keys = redisUtils.keys(PERMISSION_BY_TYPE_PRE.concat(permissionType));
            for (String key : keys) {
                redisUtils.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void remove() {
        //清除所有的缓存
        try {
            Set<String> keys = redisUtils.keys(PERMISSION_BY_TYPE_PRE.concat("*"));
            for (String key : keys) {
                redisUtils.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public boolean checkType(String type) {
        return StringUtils.equals(type,PERMISSION_BY_TYPE_PRE);
    }
}
