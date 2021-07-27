package com.landleaf.homeauto.center.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static cn.hutool.extra.servlet.ServletUtil.getBody;
import static cn.hutool.extra.servlet.ServletUtil.getParamMap;

/**
 * @ClassName LocalDataCollectPingFilter
 * @Description: 本地数采ping 云端接口
 * @Author wyl
 * @Date 2020/7/8
 * @Version V1.0
 **/
@Slf4j
public class LocalDataCollectPingFilter extends ZuulFilter {
    public static final String PATH = "/ping/cloud";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 11;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        if (uri.contains(PATH)){
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        //过滤该请求，不往下级服务转发，到此结束不进行路由
        requestContext.setSendZuulResponse(false);
        HttpServletResponse response = requestContext.getResponse();
        response.setContentType("application/json; charset=utf8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(0);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            pw.close();
        }
        return null;
    }

}
