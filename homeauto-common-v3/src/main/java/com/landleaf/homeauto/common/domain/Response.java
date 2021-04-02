package com.landleaf.homeauto.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一返回参数
 *
 * @author wenyilu
 * @param <T>
 */
@ApiModel(value = "返回对象实体")
public class Response<T> implements Serializable {


    /**
     * 请求id，系统异常时需要将此参数传递到前台去
     */
    private String requestId;

    /**
     * 请求是否处理成功
     */
    @ApiModelProperty(value = "请求是否处理成功")
    private boolean success;

    /**
     * 业务异常错误代码
     */
    private String errorCode;

    /**
     * 业务异常错误信息
     */
    private String errorMsg;

    /**
     * 提示消息，需要进行国际化
     */
    @ApiModelProperty(value = "提示消息")
    private String message;

    /**
     * 正常返回参数
     */
    @ApiModelProperty(value = "数据")
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }




}
