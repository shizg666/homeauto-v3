package com.landleaf.homeauto.center.gateway.security.handler;

import com.landleaf.homeauto.center.gateway.service.PermissionService;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 权限表达式解析处理器
 */
@Component
public class ZuulWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {
    @Resource
    private PermissionService permissionService;

    /**
     *
     * @param authentication
     * @param invocation
     * @return
     */
    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(authentication, invocation);
        standardEvaluationContext.setVariable("permissionService", permissionService);
        return standardEvaluationContext;
    }
}
