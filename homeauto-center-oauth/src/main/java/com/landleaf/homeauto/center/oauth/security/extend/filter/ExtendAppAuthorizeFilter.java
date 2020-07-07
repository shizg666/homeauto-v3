package com.landleaf.homeauto.center.oauth.security.extend.filter;

import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppAuthenticationToken;
import com.landleaf.homeauto.center.oauth.constant.LoginUrlConstant;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定义拦截器，拦截相应的请求封装相应的登录参数
 *
 * @author wenyilu
 */
public class ExtendAppAuthorizeFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * 是否只支持post
     */
    private boolean postOnly = true;

    private final static String REQUEST_POST = "POST";
    /**
     * 默认拦截的地址
     */
    private final static String DEFAULT_TOKEN_URI = LoginUrlConstant.LOGIN_APP_URL;
    /**
     * 默认拦截方法
     */
    private final static String DEFAULT_TOKEN_METHOD = "POST";

    /**
     * 手机参数
     */
    private String mobileParam = "mobile";
    /**
     * 密码参数
     */
    private String passwordParam = "password";

    public ExtendAppAuthorizeFilter() {
        super(new AntPathRequestMatcher(DEFAULT_TOKEN_URI, DEFAULT_TOKEN_METHOD));
    }

    public ExtendAppAuthorizeFilter(String tokenUri, String tokenMethod) {
        super(new AntPathRequestMatcher(tokenUri, tokenMethod));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !REQUEST_POST.equals(httpServletRequest.getMethod())) {
            throw new AuthenticationServiceException("请求方法只能为POST");
        }
        String mobile = obtainMobile(httpServletRequest);
        if (StringUtils.isEmpty(mobile)) {
            throw new AuthenticationServiceException("手机号码为空");
        }
        String password = obtainPassword(httpServletRequest);
        if (StringUtils.isEmpty(password)) {
            throw new AuthenticationServiceException("密码为空");
        }
        ExtendAppAuthenticationToken appAuthenticationToken = new ExtendAppAuthenticationToken(mobile, password);
        this.setDetails(httpServletRequest, appAuthenticationToken);
        //调用authenticationManager进行认证
        return this.getAuthenticationManager().authenticate(appAuthenticationToken);
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParam);
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParam);
    }

    private void setDetails(HttpServletRequest request, ExtendAppAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    /**
     * @param mobileParam
     */
    public void setMobileParam(String mobileParam) {
        this.mobileParam = mobileParam;
    }

    public String getMobileParam() {
        return mobileParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }
}
