package com.landleaf.homeauto.common.exception;


import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;

/**
 * 消息异常
 * .<br/>
 */
public class RocketMQException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public RocketMQException(String errCode, String message) {
        super(errCode, message);
    }

    public RocketMQException(ErrorCodeEnumConst ec) {
        super(ec);
        this.errCode = String.valueOf(ec.getCode());
    }

}
