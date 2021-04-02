package com.landleaf.homeauto.center.oauth.security.origin.handler;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName AuthExceptionEntryPoint
 * @Description: TODO
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            String message = authException.getMessage();
            Response returnResponse = ResponseUtil.returnError(message, "401");
            out.print(JSON.toJSONString(returnResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
