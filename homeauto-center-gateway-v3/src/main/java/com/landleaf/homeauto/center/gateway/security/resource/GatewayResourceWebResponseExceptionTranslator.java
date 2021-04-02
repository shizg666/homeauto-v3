package com.landleaf.homeauto.center.gateway.security.resource;

import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @ClassName GatewayResourceWebResponseExceptionTranslator
 * @Description: 异常转换
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
public class GatewayResourceWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<Response> translate(Exception e) throws Exception {

        String message = null;
        String errorCode = "4003";
        if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            errorCode = oAuth2Exception.getOAuth2ErrorCode();
            message = oAuth2Exception.getMessage();

        } else if (e.getCause() instanceof InvalidTokenException) {
            message = ErrorCodeEnumConst.TOKEN_NOT_FOUND.getMsg();
            errorCode = String.valueOf(ErrorCodeEnumConst.TOKEN_NOT_FOUND.getCode());
        } else if (e instanceof InsufficientAuthenticationException) {
            message = ErrorCodeEnumConst.AUTHENTICATION_REQUIRED.getMsg();
            errorCode = String.valueOf(ErrorCodeEnumConst.AUTHENTICATION_REQUIRED.getCode());
        } else if (e instanceof AuthenticationException) {
            String message1 = e.getMessage();
        }
        Response response = ResponseUtil.returnError(message, errorCode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
