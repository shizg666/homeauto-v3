package com.landleaf.homeauto.common.config.auth.feign;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.common.config.auth.login.context.TokenContext;
import com.landleaf.homeauto.common.config.auth.login.token.Token;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.context.RemoteHostDetailContext;
import com.landleaf.homeauto.common.domain.RemoteHostDetail;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URLEncoder;
import java.util.Map;

/**
 * feign服务调用统一增加配置:服务于透传token
 *
 * @author wenyilu
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.auth.feign", name = "enable")
public class FeignConfiguration {

    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的token
     *
     * @return
     */
    @Bean
    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

}

/**
 * Feign请求拦截器
 **/
class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    public FeignBasicAuthRequestInterceptor() {

    }

    @Override
    public void apply(RequestTemplate template) {
        Map<String, String> data = Maps.newHashMap();
        Token token = TokenContext.getToken();
        String authorizationData = "";
        if (token != null) {
//            data.put("userId",token.getUserId());
//            data.put("userType",token.getUserType());
            authorizationData = JSON.toJSONString(token);
        }
        template.header(CommonConst.AUTHORIZATION_INNER, URLEncoder.encode(authorizationData));


        setRemoteHostDetail(template);
    }


    private void setRemoteHostDetail(RequestTemplate template) {
        RemoteHostDetail remoteHostDetail = RemoteHostDetailContext.getRemoteHostDetail();
        String remoteHostDetailStr = JSON.toJSONString(remoteHostDetail);
        template.header(CommonConst.REMOTE_HOST_DETAIL, URLEncoder.encode(remoteHostDetailStr));
    }
}

