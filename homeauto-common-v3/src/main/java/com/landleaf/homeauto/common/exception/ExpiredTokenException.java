package com.landleaf.homeauto.common.exception;


import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;

/**
 * token过期异常
 * @author wenyilu
 */
public class ExpiredTokenException extends TokenException {

    private static final long serialVersionUID = -3126901220399370608L;

    public ExpiredTokenException(String errCode, String message) {
        super(errCode,message);
    }

    public ExpiredTokenException(ErrorCodeEnumConst ec) {
        super(ec);
        this.errCode = String.valueOf(ec.getCode());
    }
}
