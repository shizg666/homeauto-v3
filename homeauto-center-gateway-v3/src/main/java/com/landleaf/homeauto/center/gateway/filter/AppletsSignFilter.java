package com.landleaf.homeauto.center.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
import org.springframework.util.CollectionUtils;
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
    public static final String SECRET = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVZ3CEcGPUjKBMMAWo36i+9vLGlW+jrWVgaD+2IopqUSySKU7exFhDaqfSzOVbfuEpPq26Or3WSVEvORUkMX9cQJ6LTOridW4F8VPB5bab8jpS+d4+yzOl4nngwehSNT0J2kSMWzGKaAA+dTRYmIWRlhntpG8EJfeVRRDxaG3Y8QIDAQAB";
    public static final String SECRET2= "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJVncIRwY9SMoEwwBajfqL728saVb6OtZWBoP7YiimpRLJIpTt7EWENqp9LM5Vt+4Sk+rbo6vdZJUS85FSQxf1xAnotM6uJ1bgXxU8HltpvyOlL53j7LM6XieeDB6FI1PQnaRIxbMYpoAD51NFiYhZGWGe2kbwQl95VFEPFobdjxAgMBAAECgYAnPFoNPeLJwACc4YOq/Mm5FOtfAYGnD3NvJRGOSHXnQ9gbrmN7Fz9CvTDDqHGXXLPO/BntrV2LeAetCiWmMqWKcm9Nb9tpWnulItEFr8PD2Pr32c09bKM3KUE39bs611IBrmpz+wNWq0uhQjaodQjPVqJ7jySdgUB+Of6q6ALtPQJBANdaJPRqkYyAyg88v44anPwUGxpzgD5ft5RW1hMnxXC94ekxn1pTWpgk5xN439HGwB4l4qpr4MVoezSWrUqyLzsCQQCxmqwPCi6U7pumvUuZ0pcN2L2zC71JoayGRDkPp6CG9uDlbOirBixhpjtvejGbmzBf0KtL4Jc0YfOT2SKfy63DAkANDG4+zRJCpC8aG0E0GBK5B3LY+HSl0uDpwRU5lehVu3uryJDyRSixHVNPD7zoFhXf/cWtM9oru/fzKMoZQ5CvAkAau+aUaPr0Diq94Zaks+9q9Sow7l5y2/RFTbWtJpViW30k68zmGYrKtCQUNreK7cRNV/LA/DCmgOwSYEf298jTAkEAxNyaJhBCAvVnc6nDjR744K7gt+hFyfIthlyhkwaf2fdknxkxcpxgsmmesNh0DoRiCwkR0YJuVX3qbkbHO1zhyA==";

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
        String requestSign = request.getParameter(SING_FIELD);
        if (StringUtil.isEmpty(requestSign)) {
            sendError(requestContext,ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"签名不能为空");
            return null;
        }

        // 获取请求参数
        TreeMap<String, Object> treeMap = null;
        try {
            treeMap = getRequestTreeMap(request);
            if (CollectionUtils.isEmpty(treeMap)){
                sendError(requestContext,ErrorCodeEnumConst.SIGN_CHECK_ERROR.getCode(),"参数不能为空");
            }
            StringBuilder strBuilder = new StringBuilder();
            for (String paramKey : treeMap.keySet()) {
                append(strBuilder, treeMap, paramKey);
            }
            // 签名认证
            boolean pass = verifySign(strBuilder.toString(),requestSign);
            if (!pass) {
                log.error("################################验签失败");
//                sendError(requestContext,ErrorCodeEnumConst.SIGN_CHECK_ERROR.getCode(),ErrorCodeEnumConst.SIGN_CHECK_ERROR.getMsg());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendError(requestContext,ErrorCodeEnumConst.SIGN_CHECK_ERROR.getCode(),"参数解析异常");
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
    private TreeMap<String, Object> getRequestTreeMap(HttpServletRequest request) throws IOException {
        TreeMap<String, Object> treeMap = null;
        String method = request.getMethod();
        if (RequestMethod.GET.toString().equals(method)) {
            treeMap = new TreeMap<>();
            for (String key : request.getParameterMap().keySet()) {
                if (SING_FIELD.equals(key)){
                    continue;
                }
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
            treeMap = JSON.parseObject(body,new TypeReference<TreeMap<String, Object>>() {});
        }
        return treeMap;
    }


    /**
     * 拿入参和密钥进行签名
     * @param paramString
     * @return
     */
    private boolean verifySign(String paramString, String requestSign) {

        String sign = DigestUtils.md5Hex(paramString);
//        String orignSign = "";
//        try {
////            String sign2 = RSAEncrypt.encryptByPrivateKey(sign,SECRET2);
////            orignSign = RSAEncrypt.decryptByPublicKey(sign,SECRET);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return requestSign.equals(sign);
    }

    private  void append(StringBuilder strBuilder, TreeMap<String, Object> map, String key) {
        strBuilder.append(key);
        if (map.get(key) instanceof JSONObject) {
            TreeMap<String, Object> mapInner = JSON.parseObject(map.get(key).toString(),
                    new TypeReference<TreeMap<String, Object>>() {
                    });
            for (String innerKey : mapInner.keySet()) {
                append(strBuilder, mapInner, innerKey);
            }
        } else if (map.get(key) instanceof JSONArray) {
            JSONArray array = (JSONArray) map.get(key);
            if (!CollectionUtils.isEmpty(array)) {
                for (Object object : array) {
                    if (object instanceof JSONObject) {
                        TreeMap<String, Object> mapInner = JSON.parseObject(object.toString(),
                                new TypeReference<TreeMap<String, Object>>() {
                                });
                        for (String innerKey : mapInner.keySet()) {
                            append(strBuilder, mapInner, innerKey);
                        }
                    } else {
                        strBuilder.append(object);
                    }
                }
            }
        } else {
            strBuilder.append(map.get(key));
        }
    }
}
