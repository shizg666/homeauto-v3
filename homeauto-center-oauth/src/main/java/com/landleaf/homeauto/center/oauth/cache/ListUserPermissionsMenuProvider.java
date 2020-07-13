package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.common.domain.vo.oauth.TreeNodeVO;
import com.landleaf.homeauto.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.*;

/**
 * 用于缓存给前端的树状权限列表
 *
 * @author wenyilu*/
@Service
public class ListUserPermissionsMenuProvider implements CacheProvider {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysPermissionService sysPermissionService;

    public List<TreeNodeVO> getListUserPermissionsFromCache(String userId, Integer permissionType) {

        String key = String.format(USER_PERMISSIONS_MENU_PROVIDER_KEY, userId, permissionType);
        String cacheData = (String) redisUtil.get(key);
        if (org.springframework.util.StringUtils.isEmpty(cacheData)) {
            List<TreeNodeVO> queryReuslt = sysPermissionService.listUserPermissions(userId, permissionType);
            if (!CollectionUtils.isEmpty(queryReuslt)) {
                redisUtil.set(key, JSON.toJSONString(queryReuslt), 60 * 30);
            }
            return queryReuslt;
        }
        return JSON.parseArray(cacheData, TreeNodeVO.class);

    }

    public void remove() {
        //清除所有的权限缓存
        try {
            Set<String> keys = redisUtil.keys(USER_PERMISSIONS_MENU_PROVIDER_KEY_PRE);
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
