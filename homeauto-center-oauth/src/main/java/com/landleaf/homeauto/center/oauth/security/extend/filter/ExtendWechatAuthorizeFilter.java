package com.landleaf.homeauto.center.oauth.security.extend.filter;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.constant.LoginUrlConstant;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendWechatAuthenticationToken;
import com.landleaf.homeauto.common.domain.dto.oauth.app.AppLoginRequestDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.wechat.WechatLoginRequestDTO;
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

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.CHECK_PARAM_ERROR;

/**
 * 定义拦截器，拦截相应的请求封装相应的登录参数
 *
 * @author wenyilu
 */
public class ExtendWechatAuthorizeFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * 是否只支持post
     */
    private boolean postOnly = true;

    private final static String REQUEST_POST = "POST";
    /**
     * 默认拦截的地址
     */
    private final static String DEFAULT_TOKEN_URI = LoginUrlConstant.LOGIN_WECHAT_URL;
    /**
     * 默认拦截方法
     */
    private final static String DEFAULT_TOKEN_METHOD = "POST";

    /**
     * 用户登录凭证  由app端调微信api返回
     */
    private String code = "code";

    public ExtendWechatAuthorizeFilter() {
        super(new AntPathRequestMatcher(DEFAULT_TOKEN_URI, DEFAULT_TOKEN_METHOD));
    }

    public ExtendWechatAuthorizeFilter(String tokenUri, String tokenMethod) {
        super(new AntPathRequestMatcher(tokenUri, tokenMethod));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !REQUEST_POST.equals(httpServletRequest.getMethod())) {
            throw new AuthenticationServiceException("请求方法只能为POST");
        }
        WechatLoginRequestDTO requestParam = obtainRequestParam(httpServletRequest);
        if(requestParam==null){
            throw new AuthenticationServiceException(CHECK_PARAM_ERROR.getMsg());
        }
        String code = requestParam.getCode();
        if (StringUtils.isEmpty(code)) {
            throw new AuthenticationServiceException("code 不能为空");
        }
        ExtendWechatAuthenticationToken wechatAuthenticationToken = new ExtendWechatAuthenticationToken(code, null);
        this.setDetails(httpServletRequest, wechatAuthenticationToken);
        //调用authenticationManager进行认证
        return this.getAuthenticationManager().authenticate(wechatAuthenticationToken);
    }

    private WechatLoginRequestDTO obtainRequestParam(HttpServletRequest httpServletRequest) {

        try {
            byte[] body = StreamUtils.getByteByStream(httpServletRequest.getInputStream());
            String data = new String(body, StandardCharsets.UTF_8);
            return JSON.parseObject(data, WechatLoginRequestDTO.class);
        } catch (Exception e) {
            System.out.println("错误信息");
        }
        return null;
    }

    private String obtainCode(HttpServletRequest request) {
        return request.getParameter(code);
    }


    private void setDetails(HttpServletRequest request, ExtendWechatAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
