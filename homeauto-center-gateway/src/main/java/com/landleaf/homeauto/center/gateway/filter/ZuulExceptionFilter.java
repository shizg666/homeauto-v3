package com.landleaf.homeauto.center.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landleaf.homeauto.common.domain.Response;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.lettuce.core.dynamic.support.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.hutool.extra.servlet.ServletUtil.getBody;
import static cn.hutool.extra.servlet.ServletUtil.getParams;

/**
 * @ClassName ErrorFilter
 * @Description: zuul异常捕获
 * @Author wyl
 * @Date 2020/8/5
 * @Version V1.0
 **/
@Slf4j
public class ZuulExceptionFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            ZuulException exception = this.findZuulException(context.getThrowable());
            log.error("进入系统异常拦截", exception);
            HttpServletResponse response = context.getResponse();
            response.setContentType("application/json; charset=utf8");
            response.setStatus(exception.nStatusCode);
            PrintWriter writer = null;
            Response returnRespons = new Response<>();
            returnRespons.setSuccess(false);
            try {
                returnRespons.setErrorCode(String.valueOf(exception.nStatusCode));
                returnRespons.setErrorMsg(exception.getMessage());
                writer = response.getWriter();
                writer.write(JSON.toJSONString(returnRespons,SerializerFeature.WriteMapNullValue));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }

        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }

        return null;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (ZuulRuntimeException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause().getCause();
        } else if (ZuulException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause();
        } else {
            return ZuulException.class.isInstance(throwable) ? (ZuulException) throwable : new ZuulException(throwable, 500, (String) null);
        }
    }
}