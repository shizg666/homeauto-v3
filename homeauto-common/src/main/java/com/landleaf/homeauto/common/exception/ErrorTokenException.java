package com.landleaf.homeauto.common.exception;


import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;

/**
 * token验证失败异常
 * @author wenyilu
 */
public class ErrorTokenException extends TokenException {

    private static final long serialVersionUID = -8141186550948046737L;

    public ErrorTokenException(String errCode, String message) {
        super(errCode,message);
    }

    public ErrorTokenException(ErrorCodeEnumConst ec) {
        super(ec);
        this.errCode = String.valueOf(ec.getCode());
    }
}
