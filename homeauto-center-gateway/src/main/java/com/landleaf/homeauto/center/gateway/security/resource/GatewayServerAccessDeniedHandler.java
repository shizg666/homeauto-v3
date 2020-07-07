package com.landleaf.homeauto.center.gateway.security.resource;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName GatewayServerAccessDeniedHandler
 * @Description: 授权失败(forbidden)时返回信息
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Slf4j
public class GatewayServerAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String message = "无权限,不允许访问";
        Response returnResponse = ResponseUtil.returnError(message, "401");
        out.print(JSON.toJSONString(returnResponse));
    }
}
