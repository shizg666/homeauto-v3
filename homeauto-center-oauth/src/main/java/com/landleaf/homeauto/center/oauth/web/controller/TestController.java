package com.landleaf.homeauto.center.oauth.web.controller;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppAuthenticationToken;
import com.landleaf.homeauto.center.oauth.service.ICustomerThirdSourceService;
import com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource;
import com.landleaf.homeauto.common.enums.oauth.CustomerThirdTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName TestController
 * @Description: token相关操作
 * @Author wyl
 * @Date 2020/6/9
 * @Version V1.0
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ICustomerThirdSourceService customerThirdSourceService;
    /**
     * 根據openId獲取
     */
    @GetMapping("/record/third")
    public void recordThirdCustomer() {
        CustomerThirdSource record = customerThirdSourceService.getRecord("ooNtK5KMKLtfvj0QU4aMdzLY-0LE", CustomerThirdTypeEnum.WECHAT.getCode());
        System.out.println(record);
    }

}
