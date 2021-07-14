package com.landleaf.homeauto.center.gateway.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.landleaf.homeauto.center.gateway.service.PriKeyService;
import com.landleaf.homeauto.center.gateway.wrapper.BodyReaderRequestWrapper;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

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
    public static final String APPKEY_FIELD = "appKey";
    
    @Resource
    private PriKeyService priKeyServiceImpl;

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
        String appkey = request.getParameter(APPKEY_FIELD);
        if (StringUtil.isEmpty(requestSign)) {
            sendError(requestContext,ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"签名不能为空");
            return null;
        }
        if (StringUtil.isEmpty(appkey)) {
            sendError(requestContext,ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"appkey不能为空");
            return null;
        }
        
        // 获取privateKey
        String privateKey = priKeyServiceImpl.getPriKeyByAppKey(appkey);
        if (StringUtil.isEmpty(privateKey)) {
        	sendError(requestContext,ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"appkey异常，请核验或联系相关人员解决");
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
            //todo 秘钥串
            strBuilder.append(privateKey);
            // 签名认证
            boolean pass = verifySign(strBuilder.toString(),requestSign);
            if (!pass) {
                log.error("################################验签失败");
                sendError(requestContext,ErrorCodeEnumConst.SIGN_CHECK_ERROR.getCode(),ErrorCodeEnumConst.SIGN_CHECK_ERROR.getMsg());
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
                if (APPKEY_FIELD.equals(key)){
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

        log.info("待签名的字符串为:{}", paramString);
        String sign = DigestUtils.md5Hex(paramString).toLowerCase();
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
