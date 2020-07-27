package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.ISysRoleService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
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
import static com.landleaf.homeauto.common.constance.RedisCacheConst.KEY_SYS_ROLE;

/**
 * 后台用户拥有角色缓存
 **/
@Service
public class SysRoleCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 根据角色ID获取角色
     *
     * @param roleId 角色ID
     * @return
     */
    public SysRole getUserRole(String roleId) {
        boolean hasKey = redisUtils.hasKey(KEY_SYS_ROLE);
        if (hasKey) {
            Object hget = redisUtils.hget(KEY_SYS_ROLE, roleId);
            if (hget == null) {
                return getSysRoleByDB(roleId);
            }
            return JSON.parseObject(JSON.toJSONString(hget), SysRole.class);
        }
        return getSysRoleByDB(roleId);
    }

    private SysRole getSysRoleByDB(String roleId) {
        SysRole result = null;
        SysRole sysRole = sysRoleService.getById(roleId);
        if (sysRole != null) {
            result = new SysRole();
            BeanUtils.copyProperties(sysRole, result);
            redisUtils.hset(KEY_SYS_ROLE, roleId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存所有角色
     */
    public void cacheAllRole() {
        List<SysRole> allRole = sysRoleService.list();
        redisUtils.del(KEY_SYS_ROLE);
        if (!CollectionUtils.isEmpty(allRole)) {
            Map<String, Object> roleMap = allRole.stream().collect(Collectors.toMap(SysRole::getId, i -> {
                return i;
            }, (o, n) -> o));
            redisUtils.hmset(KEY_SYS_ROLE, roleMap);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllRole();
    }

    public void remove(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            redisUtils.del(KEY_SYS_ROLE);
        } else {
            redisUtils.hdel(KEY_SYS_ROLE, roleId);
        }
    }
}
