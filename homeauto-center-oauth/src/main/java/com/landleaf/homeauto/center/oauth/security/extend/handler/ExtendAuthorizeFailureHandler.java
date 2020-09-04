package com.landleaf.homeauto.center.oauth.security.extend.handler;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.*;

/**
 * 登录失败处理器
 *
 * @author wenyilu
 */

@Slf4j
public class ExtendAuthorizeFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final String ACCEPT_TYPE_HTML = "text/html";
    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String type = request.getHeader("Accept");
        if (!type.contains(ACCEPT_TYPE_HTML)) {
            response.setContentType(CONTENT_TYPE_JSON);
            Writer writer = response.getWriter();
            ErrorCodeEnumConst passwordInputErroe = PASSWORD_INPUT_ERROE;
            String message = exception.getMessage();
            Response errorResponse = ResponseUtil.returnError(message, "4003");
            errorResponse.setResult(null);
            errorResponse.setRequestId(null);
            if (StringUtils.equals(message, PASSWORD_INPUT_ERROE.getMsg())) {
                errorResponse = ResponseUtil.returnError(PASSWORD_INPUT_ERROE.getMsg(),String.valueOf( PASSWORD_INPUT_ERROE.getCode()));
            } else if (StringUtils.equals(message, USER_NOT_FOUND.getMsg())) {
                errorResponse = ResponseUtil.returnError(USER_NOT_FOUND.getMsg(), "4003");
            } else if (StringUtils.equals(message, USER_INACTIVE_ERROE.getMsg())) {
                errorResponse = ResponseUtil.returnError(USER_INACTIVE_ERROE.getMsg(), String.valueOf(USER_INACTIVE_ERROE.getCode()));
            } else if (StringUtils.equals(message, NO_ANY_MANU_PERMISSION_ERROR.getMsg())) {
                errorResponse = ResponseUtil.returnError(NO_ANY_MANU_PERMISSION_ERROR.getMsg(), String.valueOf(NO_ANY_MANU_PERMISSION_ERROR.getCode()));
            }
            writer.write(JSONObject.toJSONString(errorResponse, SerializerFeature.WriteMapNullValue));
            log.debug(exception.getMessage());
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
