package com.landleaf.homeauto.center.oauth.security.origin.translator;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @ClassName AuthWebResponseExceptionTranslator
 * @Description: 异常转换
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Component
public class AuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<Response> translate(Exception e) throws Exception {

        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        String oAuth2ErrorCode = oAuth2Exception.getOAuth2ErrorCode();
        String message = oAuth2Exception.getMessage();
        Response response = ResponseUtil.returnError(message, oAuth2ErrorCode);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
