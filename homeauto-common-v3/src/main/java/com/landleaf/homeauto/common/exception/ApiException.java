package com.landleaf.homeauto.common.exception;

/**
 * Api异常
 *
 * @author Yujiumin
 * @version 2020/9/25
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public Integer errCode() {
        return 90001;
    }
}
