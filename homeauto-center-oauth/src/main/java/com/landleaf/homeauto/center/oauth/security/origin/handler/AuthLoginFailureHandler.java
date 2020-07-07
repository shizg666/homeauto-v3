package com.landleaf.homeauto.center.oauth.security.origin.handler;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthLoginFailureHandler
 * @Description: 登录失败异常处理
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Slf4j
@Component
public class AuthLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof BadCredentialsException) {
            response.getWriter().write(JSON.toJSONString(
                    ResponseUtil.returnError(null, "户名或密码错误", "400")));
        }
    }
}