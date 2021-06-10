package com.landleaf.homeauto.center.oauth.security.origin.config;

import com.landleaf.homeauto.center.oauth.constant.LoginUrlConstant;
import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendAppNonSmartSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendAppSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendWebSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendWechatSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.origin.encoder.AuthNoEncryptPasswordEncoder;
import com.landleaf.homeauto.center.oauth.security.origin.filter.AuthSupportJsonAuthenticationFilter;
import com.landleaf.homeauto.center.oauth.security.origin.handler.AuthLoginUrlAuthenticationEntryPoint;
import com.landleaf.homeauto.center.oauth.security.origin.matchers.GatewayOAuth2RequestedMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ClassName AuthSecurityConfig
 * @Description: 针对认证配置过滤器
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Configuration
@EnableWebSecurity
@Order(2)
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler authLoginInSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authLoginFailureHandler;
    @Autowired
    private UserDetailsService authUserDetailsService;
    @Autowired
    private ExtendAppSecurityConfigurerAdapter extendAppSecurityConfigurerAdapter;
    @Autowired
    private ExtendAppNonSmartSecurityConfigurerAdapter extendAppNonSmartSecurityConfigurerAdapter;
    @Autowired
    private ExtendWebSecurityConfigurerAdapter extendWebSecurityConfigurerAdapter;
    @Autowired(required = false)
    private ExtendWechatSecurityConfigurerAdapter extendWechatSecurityConfigurerAdapter;
    @Value("${homeauto.security.oauth2.extend.wechat.enable}")
    private  boolean wechatEnable;
    /**
     * 白名单路径
     */
    @Value("${homeauto.security.excluded.paths}")
    private String[] excludePaths;
    /**
     * oauth2需要登录认证的路径
     */
    @Value("${homeauto.security.requestPatterns}")
    private String[] requestPatterns;
    /**
     * oauth2不需要登录验证的白名单
     */
    @Value("${homeauto.security.whitePatterns}")
    private String[] whitePatterns;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .requestMatcher(gatewayOAuth2RequestedMatcher())
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/logout/**").permitAll()
                .antMatchers(excludePaths).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        // 原始登录配置：这里未使用
        http.formLogin()
                // 用户名登录地址
                .loginProcessingUrl(LoginUrlConstant.LOGIN_ORIGIN_URL)
                //登录成功处理器
                .successHandler(authLoginInSuccessHandler)
                .failureHandler(authLoginFailureHandler)
                .and()
                .exceptionHandling()
                // 登录成功后跳转
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint());


        http.httpBasic().disable();
        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(supportJsonAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

        // 配置自定义的扩展登录
        ExtendWebSecurityConfigurerAdapter apply = http.apply(extendAppSecurityConfigurerAdapter)
                .and()
                .apply(extendAppNonSmartSecurityConfigurerAdapter)
                .and()
                .apply(extendWebSecurityConfigurerAdapter);
        if(wechatEnable){
            apply.and().apply(extendWechatSecurityConfigurerAdapter);
        }



        // 开启匿名登录--在auth只做权限认证，不去管登录问题，登录在zuul处理
    }

    /**
     * authorizationServerConfig中需要
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    AuthSupportJsonAuthenticationFilter supportJsonAuthenticationFilter() throws Exception {
        AuthSupportJsonAuthenticationFilter filter = new AuthSupportJsonAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authLoginInSuccessHandler);
        filter.setAuthenticationFailureHandler(authLoginFailureHandler);
        filter.setFilterProcessesUrl(LoginUrlConstant.LOGIN_ORIGIN_URL);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new AuthLoginUrlAuthenticationEntryPoint(LoginUrlConstant.LOGIN_ORIGIN_URL);
    }

    /**
     * 密码加密方式-全局的
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  方便测试取消掉密码加密
        return new AuthNoEncryptPasswordEncoder();
    }

    @Bean
    @Primary
    public GatewayOAuth2RequestedMatcher gatewayOAuth2RequestedMatcher() {
        GatewayOAuth2RequestedMatcher gatewayOAuth2RequestedMatcher = new GatewayOAuth2RequestedMatcher(null, requestPatterns);
        gatewayOAuth2RequestedMatcher.whiteMatcher(whitePatterns);
        return gatewayOAuth2RequestedMatcher;
    }
}
