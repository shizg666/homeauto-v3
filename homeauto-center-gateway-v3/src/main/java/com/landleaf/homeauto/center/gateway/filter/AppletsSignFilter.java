package com.landleaf.homeauto.center.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landleaf.homeauto.center.gateway.utils.RSAEncrypt;
import com.landleaf.homeauto.center.gateway.wrapper.BodyReaderRequestWrapper;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.lettuce.core.dynamic.support.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import static cn.hutool.extra.servlet.ServletUtil.getBody;
import static cn.hutool.extra.servlet.ServletUtil.getParamMap;

/**
 * @ClassName AddTokenFilter
 * @Description: TODO
 * @Author wyl
 * @Date 2020/7/8
 * @Version V1.0
 **/
@Slf4j
@Component
public class AppletsSignFilter extends ZuulFilter {

    public static final String PATH = "/jh/applets";
    public static final String SING_FIELD = "sign";
    public static final String SECRET = "qwertyuiopasdfghjkl";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
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
        HttpServletRequest request = requestContext.getRequest();
        String requestSign = request.getHeader(SING_FIELD);
        if (StringUtil.isEmpty(requestSign)) {
            throw new ZuulException("123",1,null);
//            sendError(requestContext,ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"签名不能为空");
//            return null;
        }
        // 获取请求参数
        TreeMap<String, String> treeMap = null;
        try {
            treeMap = getRequestTreeMap(request);
        } catch (IOException e) {
            e.printStackTrace();
//            throw new BusinessException(ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getCode(),"参数解析异常");
//            sendError("参数解析异常",ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getCode());
            return null;

        }
        // 签名认证
        boolean pass = verifySign(treeMap,requestSign);
        if (!pass) {
//            throw new BusinessException(ErrorCodeEnumConst.SIGN_CHECK_ERROR.getCode(),ErrorCodeEnumConst.SIGN_CHECK_ERROR.getMsg());
//            sendError(ErrorCodeEnumConst.SIGN_CHECK_ERROR.getMsg(),ErrorCodeEnumConst.SIGN_CHECK_ERROR.getCode());
            return null;
        }
        return null;
    }

    /**
     * 发送错误消息
     *
     * @param requestContext
     * @param status
     * @param msg
     */
    private void sendError(RequestContext requestContext, int status, String msg) {
        //过滤该请求，不往下级服务转发，到此结束不进行路由
        requestContext.setSendZuulResponse(false);
        HttpServletResponse response = requestContext.getResponse();
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setContentType("application/json; charset=utf8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        try {
            Response returnRespons = new Response<>();
            returnRespons.setSuccess(false);
            returnRespons.setErrorCode(String.valueOf(status));
            returnRespons.setErrorMsg(msg);
            pw = response.getWriter();
            pw.write(JSONObject.toJSONString(returnRespons));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            pw.close();
        }
    }

    /**
     * 构建客户端传入的参数
     * @param request
     * @return
     * @throws IOException
     */
    private TreeMap<String, String> getRequestTreeMap(HttpServletRequest request) throws IOException {
        TreeMap<String, String> treeMap = null;
        String method = request.getMethod();
        if (RequestMethod.GET.toString().equals(method)) {
            treeMap = new TreeMap<>();
            for (String key : request.getParameterMap().keySet()) {
                treeMap.put(key, request.getParameter(key));
            }
        } else {
            BodyReaderRequestWrapper servletRequestWrapper;
            if (request instanceof BodyReaderRequestWrapper) {
                servletRequestWrapper = (BodyReaderRequestWrapper) request;
            } else {
                servletRequestWrapper = new BodyReaderRequestWrapper(request);
            }
            String body = servletRequestWrapper.getBody();
            treeMap = JSON.parseObject(body,TreeMap.class);
//            treeMap = objectMapper.readValue(body, new TypeReference<TreeMap<String, String>>() {});
        }
        return treeMap;
    }


    /**
     * 拿入参和密钥进行签名
     * @param paramMap
     * @return
     */
    private boolean verifySign(Map<String, String> paramMap, String requestSign) {
        if (paramMap == null || paramMap.isEmpty()) {
            return false;
        }
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        final StringBuilder sb = new StringBuilder();
        paramMap.forEach((key, value) -> sb.append(key).append(value));
        String paramString = sb.toString();
        String sign = DigestUtils.md5Hex(paramString);
        String orignSign = "";
        try {
            orignSign = RSAEncrypt.decrypt(sign,SECRET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign.equals(orignSign);
    }
}
