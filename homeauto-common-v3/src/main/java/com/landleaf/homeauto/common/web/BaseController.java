package com.landleaf.homeauto.common.web;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.BaseDto;
import com.landleaf.homeauto.common.exception.ApiException;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.IpUtil;
import com.landleaf.homeauto.common.util.VerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求信息的基类
 *
 * @author wenyilu
 */
@Slf4j
public abstract class BaseController {

    static final String SUCCESS_TIP = "操作成功";

    /**
     * 校验参数,并将ip写入dto内
     *
     * @param req request请求信息
     * @param t   参数
     * @return 校验后的参数
     * @throws BusinessException 参数校验失败则已异常的形式返回
     */
    public <T extends BaseDto> T getReqBindIp(HttpServletRequest req, T t) throws BusinessException {
        String ip = IpUtil.getIp(req);
        t.setIp(ip);
        VerifyUtil.verify(t);
        return t;
    }

    /**
     * 校验参数
     *
     * @param t 参数
     * @return 校验后的参数
     * @throws BusinessException 参数校验失败则已异常的形式返回
     */
    public <T extends BaseDto> T getReq(T t) throws BusinessException {
        VerifyUtil.verify(t);
        return t;
    }


    /**
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 请求成功，不带返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:20
     * @version 1.0
     */
    public <T> Response<T> returnSuccess() {
        return returnSuccessMessage(SUCCESS_TIP);
    }

    /**
     * @param successMsg
     * @description 带成功提示的返回
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public <T> Response<T> returnSuccessMessage(String successMsg) {
        return returnSuccess(null, successMsg);
    }

    /**
     * @param object
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 请求成功带返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public <T> Response<T> returnSuccess(T object) {
        return returnSuccess(object, SUCCESS_TIP);
    }



    /**
     * @param object
     * @param successMsg
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 带成功提示和返回参数的结果
     * @author wyl
     * @date 2019/3/21 0021 9:21
     * @version 1.0
     */
    public <T> Response<T> returnSuccess(T object, String successMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setMessage(successMsg);
        response.setResult(object);
        return response;
    }


    /**
     * @param exception
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 统一的请求异常处理，所有异常都转换为json输入到前台，前端根据返回结果进行判断如何展示异常信息
     * @author wyl
     * @date 2019/3/21 0021 9:22
     * @version 1.0
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> Response<T> handlerException(Exception exception) {
        log.error(exception.getMessage(), exception);
        Response<T> response = new Response<>();
        response.setSuccess(false);
        if (exception instanceof BusinessException) {    //业务异常
            response.setErrorCode(((BusinessException) exception).getErrorCode());
            response.setErrorMsg(exception.getMessage());
        } else if (exception instanceof ApiException) {
            // 接口异常
            ApiException apiException = (ApiException) exception;
            response.setErrorCode(apiException.errCode().toString());
            response.setErrorMsg(apiException.getMessage());
        } else if (exception instanceof MethodArgumentNotValidException) { //参数校验失败异常
            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            StringBuffer errorMsg = new StringBuffer();
            bindingResult.getFieldErrors().forEach(fieldError -> errorMsg.append(fieldError.getDefaultMessage()).append("\n"));
            response.setErrorCode(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()));
            response.setErrorMsg(errorMsg.toString());
        } else if (exception instanceof ExcelAnalysisException) {
            response.setErrorCode(((BusinessException) exception).getErrorCode());
            response.setErrorMsg(exception.getMessage());
        } else { //未捕获异常
            response.setMessage(exception.getMessage());
            response.setErrorCode(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getCode()));
//            response.setErrorMsg(ExceptionUtils.getStack(exception));
        }
        return response;
    }

    /**
     * @return com.landleaf.leo.controller.dto.response.Response
     * @description 请求成功，不带返回参数
     * @author wyl
     * @date 2019/3/21 0021 9:20
     * @version 1.0
     */
    public <T> Response<T> returnError(String code, String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(code);
        response.setErrorMsg(message);
        return response;
    }
}
