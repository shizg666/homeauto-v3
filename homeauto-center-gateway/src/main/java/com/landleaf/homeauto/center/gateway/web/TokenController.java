package com.landleaf.homeauto.center.gateway.web;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @ClassName ExtractTokenController
 * @Description: 解析token单独controller
 * @Author wyl
 * @Date 2020/9/25
 * @Version V1.0
 **/
@RestController
@RequestMapping("/token")
public class TokenController extends BaseController {

    @Autowired
    TokenStore jwtTokenStore;

    @GetMapping("/extract")
    public Response checkTokenLedge(@RequestParam String token) {
        Response response = new Response();
        response.setSuccess(true);
        response.setResult(true);
        try {
            OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(token);
            if (oAuth2AccessToken == null || oAuth2AccessToken.getExpiration().before(new Date())) {
                response.setResult(false);
            }
        } catch (Exception e) {
            response.setResult(false);
            response.setErrorMsg(e.getMessage());
        }
        return response;
    }

}
