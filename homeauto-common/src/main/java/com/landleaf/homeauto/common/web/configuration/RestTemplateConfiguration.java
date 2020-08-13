package com.landleaf.homeauto.common.web.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 区别于feign底层使用的restTemplate
 *
 * @author wenyilu
 */
@Configuration
public class RestTemplateConfiguration {



    @Bean("outRestTemplate")
    public RestTemplate outRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // httpClient连接池配置
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager();
        pollingConnectionManager.setMaxTotal(1500);
        pollingConnectionManager.setDefaultMaxPerRoute(1500);
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        // 连接超时时间
        requestBuilder = requestBuilder.setConnectTimeout(30 * 1000);
        // 从连接池中获取连接超时时间
        requestBuilder = requestBuilder.setConnectionRequestTimeout(30 * 1000);
        // 读取超时时间
        requestBuilder = requestBuilder.setSocketTimeout(60 * 1000);
        // 初始化httpClient
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestBuilder.build())
                .setConnectionManager(pollingConnectionManager).build();
        // 连接工厂
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }
}
