package com.landleaf.homeauto.center.oauth.security.extend.filter;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.constant.LoginUrlConstant;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppAuthenticationToken;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppNonSmartAuthenticationToken;
import com.landleaf.homeauto.common.domain.dto.oauth.app.AppLoginRequestDTO;
import com.landleaf.homeauto.common.util.StreamUtils;
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
import java.nio.charset.StandardCharsets;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.*;

/**
 * 定义拦截器，拦截相应的请求封装相应的登录参数
 *
 * @author wenyilu
 */
public class ExtendAppNonSmartAuthorizeFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * 是否只支持post
     */
    private boolean postOnly = true;

    private final static String REQUEST_POST = "POST";
    /**
     * 默认拦截的地址
     */
    private final static String DEFAULT_TOKEN_URI = LoginUrlConstant.LOGIN_APP_NON_SMART_URL;
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

    public ExtendAppNonSmartAuthorizeFilter() {
        super(new AntPathRequestMatcher(DEFAULT_TOKEN_URI, DEFAULT_TOKEN_METHOD));
    }

    public ExtendAppNonSmartAuthorizeFilter(String tokenUri, String tokenMethod) {
        super(new AntPathRequestMatcher(tokenUri, tokenMethod));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !REQUEST_POST.equals(httpServletRequest.getMethod())) {
            throw new AuthenticationServiceException(METHOD_NOT_POST_ERROR.getMsg());
        }
        AppLoginRequestDTO requestParam= obtainRequestParam(httpServletRequest);
        if(requestParam==null){
            throw new AuthenticationServiceException(CHECK_PARAM_ERROR.getMsg());
        }
        String password = requestParam.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new AuthenticationServiceException(PASSWORD_EMPTY_ERROR.getMsg());
        }
        String mobile = requestParam.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            throw new AuthenticationServiceException(PHONE_EMPTY_ERROR.getMsg());
        }
        ExtendAppNonSmartAuthenticationToken appAuthenticationToken = new ExtendAppNonSmartAuthenticationToken(mobile, password);
        this.setDetails(httpServletRequest, appAuthenticationToken);
        //调用authenticationManager进行认证
        return this.getAuthenticationManager().authenticate(appAuthenticationToken);
    }

    private AppLoginRequestDTO obtainRequestParam(HttpServletRequest httpServletRequest) {

        try {
            byte[] body = StreamUtils.getByteByStream(httpServletRequest.getInputStream());
            String data = new String(body, StandardCharsets.UTF_8);
            return JSON.parseObject(data, AppLoginRequestDTO.class);
        } catch (Exception e) {
            System.out.println("错误信息");
        }
        return null;
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParam);
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParam);
    }

    private void setDetails(HttpServletRequest request, ExtendAppNonSmartAuthenticationToken authRequest) {
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
