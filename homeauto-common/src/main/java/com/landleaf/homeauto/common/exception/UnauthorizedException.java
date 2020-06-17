package com.landleaf.homeauto.common.exception;


import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;

/**
 * 没有权限的异常
 * @author wenyilu
 */
public class UnauthorizedException extends TokenException {

    private static final long serialVersionUID = 5312033869876406177L;

    public UnauthorizedException(String errCode, String message) {
        super(errCode,message);
    }

    public UnauthorizedException(ErrorCodeEnumConst ec) {
        super(ec);
        this.errCode = String.valueOf(ec.getCode());
    }
}
