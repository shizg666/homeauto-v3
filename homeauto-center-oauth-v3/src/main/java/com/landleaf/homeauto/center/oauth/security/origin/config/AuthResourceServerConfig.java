package com.landleaf.homeauto.center.oauth.security.origin.config;//package com.landleaf.demo.wenyilu.oath2.server.config.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @ClassName AuthResourceServerConfig
 * @Description: 资源服务配置
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Configuration
@EnableResourceServer
public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        /********************************表单登录 *********************************************************/
        //  前后端交互无论是app还是web或是大屏用json形式，无须页面跳转
        http.authorizeRequests()
                .anyRequest()
                .permitAll()//以上的请求都不需要认证
                .and()
                .csrf().disable();

    }



}
