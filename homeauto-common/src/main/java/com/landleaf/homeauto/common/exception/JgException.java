package com.landleaf.homeauto.common.exception;


import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;

/**
 * @author Lokiy
 * @date 2019/8/15 16:37
 * @description: 极光异常
 */
public class JgException extends BusinessException {


    public JgException(String errCode, String message) {
        super(errCode,message);
    }

    public JgException(ErrorCodeEnumConst ec) {
        super(ec);
        this.errCode = String.valueOf(ec.getCode());
    }
}
