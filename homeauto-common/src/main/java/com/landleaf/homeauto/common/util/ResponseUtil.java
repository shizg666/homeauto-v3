package com.landleaf.homeauto.common.util;

import com.landleaf.homeauto.common.domain.Response;

/**
 * @ClassName ResponseUtil
 * @Description: TODO
 * @Author wyl
 * @Date 2020/7/1
 * @Version V1.0
 **/
public class ResponseUtil {



    public static Response returnSuccess(Object result) {
        Response returnResponse = new Response();
        returnResponse.setSuccess(true);
        returnResponse.setResult(result);
        return returnResponse;
    }

    public static Response returnError(Object result, String errorMsg, String errorCode) {
        Response returnResponse = new Response();
        returnResponse.setSuccess(false);
        returnResponse.setErrorMsg(errorMsg);
        returnResponse.setErrorCode(errorCode);
        returnResponse.setResult(result);
        return returnResponse;
    }

    public static Response returnError(String errorMsg, String errorCode) {
        return returnError(null, errorMsg, errorCode);
    }
}
