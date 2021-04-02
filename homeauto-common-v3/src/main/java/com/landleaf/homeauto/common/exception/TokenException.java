package com.landleaf.homeauto.common.exception;


import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;

/**
 * @author wenyilu
 */
public abstract class TokenException extends BusinessException {

    private static final long serialVersionUID = 3952712885059534246L;

    public TokenException(String errCode, String message) {
        super(errCode,message);
    }

    public TokenException(ErrorCodeEnumConst ec) {
        super(ec.getMsg());
        this.errCode = String.valueOf(ec.getCode());
    }
}
