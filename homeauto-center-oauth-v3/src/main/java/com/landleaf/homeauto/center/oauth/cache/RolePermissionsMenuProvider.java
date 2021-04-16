package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.common.domain.vo.oauth.TreeNodeVO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.*;

/**
 * 用于缓存给前端的树状权限列表(角色菜单权限列表)
 *
 * @author wenyilu*/
@Service
public class RolePermissionsMenuProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysPermissionService sysPermissionService;

    public List<TreeNodeVO> getListUserPermissionsFromCache(String roleId, String userId, Integer permissionType) {

        String key = String.format(ROLE_PERMISSIONS_MENU_PROVIDER_KEY, roleId, permissionType);
        String cacheData = (String) redisUtils.get(key);
        if (org.springframework.util.StringUtils.isEmpty(cacheData)) {
            List<TreeNodeVO> queryReuslt = sysPermissionService.listUserPermissions(userId, permissionType);
            if (!CollectionUtils.isEmpty(queryReuslt)) {
                redisUtils.set(key, JSON.toJSONString(queryReuslt), 60 * 30);
            }
            return queryReuslt;
        }
        return JSON.parseArray(cacheData, TreeNodeVO.class);

    }

    public void remove() {
        //清除所有的权限缓存
        try {
            Set<String> keys = redisUtils.keys(ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE+"*");
            for (String key : keys) {
                redisUtils.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void remove(String roleId) {
        if(StringUtils.isEmpty(roleId)){
             remove();
             return;
        }
        try {
            Set<String> keys = redisUtils.keys(String.format("%s:%s*",ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE,roleId));
            if(CollectionUtils.isEmpty(keys)){
                return;
            }
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
        return StringUtils.equals(type,ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE);
    }
}
