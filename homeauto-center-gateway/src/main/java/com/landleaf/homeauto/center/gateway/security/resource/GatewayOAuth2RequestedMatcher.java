package com.landleaf.homeauto.center.gateway.security.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName GatewayOAuth2RequestedMatcher
 * @Description: 自定义需要登录拦截rul匹配器
 * @Author wyl
 * @Date 2020/7/15
 * @Version V1.0
 **/
public class GatewayOAuth2RequestedMatcher implements RequestMatcher {
    private final Log logger = LogFactory.getLog(getClass());
    private final List<RequestMatcher> requestMatchers;

    private List<RequestMatcher> whiteMatchers;

    private String[] whitePatterns;


    public static List<RequestMatcher> antMatchers(HttpMethod httpMethod,
                                                   String... antPatterns) {
        String method = httpMethod == null ? null : httpMethod.toString();
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String pattern : antPatterns) {
            matchers.add(new AntPathRequestMatcher(pattern, method));
        }
        return matchers;
    }

    public GatewayOAuth2RequestedMatcher(HttpMethod httpMethod,
                                         String... antPatterns) {
        String method = httpMethod == null ? null : httpMethod.toString();
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String pattern : antPatterns) {
            matchers.add(new AntPathRequestMatcher(pattern, method));
        }
        Assert.notEmpty(matchers, "requestMatchers must contain a value");
        if (matchers.contains(null)) {
            throw new IllegalArgumentException(
                    "requestMatchers cannot contain null values");
        }
        this.requestMatchers = matchers;
    }

    /**
     * Creates a new instance
     *
     * @param requestMatchers the {@link RequestMatcher} instances to try
     */
    public GatewayOAuth2RequestedMatcher(List<RequestMatcher> requestMatchers) {
        Assert.notEmpty(requestMatchers, "requestMatchers must contain a value");
        if (requestMatchers.contains(null)) {
            throw new IllegalArgumentException(
                    "requestMatchers cannot contain null values");
        }
        this.requestMatchers = requestMatchers;
    }

    /**
     *设置白名单
     */
    public GatewayOAuth2RequestedMatcher whiteMatcher(String... whitePatterns) {
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String pattern : whitePatterns) {
            matchers.add(new AntPathRequestMatcher(pattern, null));
        }
        Assert.notEmpty(matchers, "whiteMatchers must contain a value");
        if (matchers.contains(null)) {
            throw new IllegalArgumentException(
                    "whiteMatchers cannot contain null values");
        }
        this.whiteMatchers = matchers;
        return this;
    }

    /**
     * Creates a new instance
     *
     * @param requestMatchers the {@link RequestMatcher} instances to try
     */
    public GatewayOAuth2RequestedMatcher(RequestMatcher... requestMatchers) {
        this(Arrays.asList(requestMatchers));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : whiteMatchers) {
            if (logger.isDebugEnabled()) {
                logger.debug("Trying to match using " + matcher);
            }
            // 先走白名单
            if (matcher.matches(request)) {
                logger.debug("matched");
                return false;
            }
        }
        for (RequestMatcher matcher : requestMatchers) {
            if (logger.isDebugEnabled()) {
                logger.debug("Trying to match using " + matcher);
            }
            if (matcher.matches(request)) {
                logger.debug("matched");
                return true;
            }
        }
        logger.debug("No matches found");
        return false;
    }

    @Override
    public String toString() {
        return "GatewayOAuth2RequestedMatcher [requestMatchers=" + requestMatchers + "]";
    }

}