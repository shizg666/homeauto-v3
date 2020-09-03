package com.landleaf.homeauto.common.web.context;

import com.landleaf.homeauto.common.domain.HomeAutoToken;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lokiy
 * @date 2019/8/19 12:07
 * @description:
 */
public class TokenContextUtil {


    /**
     * 获取用户信息
     * @return
     */
    public static String getUser(){
        if(TokenContext.getToken() == null) {
            return "0_0";
        }
        String userId = StringUtils.isBlank(TokenContext.getToken().getUserId()) ?  "0" :
                TokenContext.getToken().getUserId();
        String userType = StringUtils.isBlank(TokenContext.getToken().getUserType())? "0" :
                TokenContext.getToken().getUserType();
        return userType+ "_" + userId;
    }



    /**
     * 获取用户信息
     * @return
     */
    public static String getUserId(){
        if(TokenContext.getToken() == null){
            return "0";
        }
        return StringUtils.isBlank(TokenContext.getToken().getUserId()) ?  "0" :
                TokenContext.getToken().getUserName();
    }

    public static HomeAutoToken getWebUser() {
        HomeAutoToken token = TokenContext.getToken();
        if(TokenContext.getToken() == null) {
           return new HomeAutoToken();
        }
        return token;
    }
}
