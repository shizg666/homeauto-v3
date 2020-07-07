package com.landleaf.homeauto.center.gateway.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.gateway.service.PermissionService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName PermissionServiceImpl
 * @Description: TODO
 * @Author wyl
 * @Date 2020/7/3
 * @Version V1.0
 **/

@Service
public class PermissionServiceImpl implements PermissionService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication auth) {
        Object principal = auth.getPrincipal();
        boolean hasPermission = false;
        // 读取用户所拥有的权限菜单
        List<String>  hasPermisssionUrls = Lists.newArrayList();
        if(CollectionUtils.isEmpty(hasPermisssionUrls)){
            return true;
        }
        for (String hasPermisssionUrl : hasPermisssionUrls) {
            if (antPathMatcher.match(hasPermisssionUrl, request.getRequestURI())) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;

    }
}
