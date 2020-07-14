package com.landleaf.homeauto.center.gateway.security.resource;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName SecurityAuthenticationEntryPoint
 * @Description: 匿名访问异常处理
 * @Author wyl
 * @Date 2020/6/5
 * @Version V1.0
 **/
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Spring Securtiy异常", authException);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String message = authException.getMessage();
        Response returnResponse = ResponseUtil.returnError(message, String.valueOf(ErrorCodeEnumConst.AUTHENTICATION_TOKEN_REQUIRED.getCode()));
        out.print(JSON.toJSONString(returnResponse));
    }
}
