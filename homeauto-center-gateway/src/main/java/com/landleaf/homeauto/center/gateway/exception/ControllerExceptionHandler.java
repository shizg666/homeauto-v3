package com.landleaf.homeauto.center.gateway.exception;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName ControllerExceptionHandler
 * @Description: 网关层controller全局异常
 * @Author wyl
 * @Date 2020/8/6
 * @Version V1.0
 **/
@RestController
@Slf4j
public class ControllerExceptionHandler extends BaseController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 默认错误
     */
    private static final String path_default = "/error";

    @Override
    public String getErrorPath() {
        return path_default;
    }

    /**
     * JSON格式错误信息
     */
    @RequestMapping(value = path_default, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, true);

        return returnError(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getCode()), errorAttributes != null ? JSON.toJSONString(errorAttributes) : null);
    }

}
