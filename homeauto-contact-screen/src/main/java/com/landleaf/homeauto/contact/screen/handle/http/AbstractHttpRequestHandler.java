package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenErrorCodeEnumConst;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;

/**
 * @author wenyilu
 */
public abstract class AbstractHttpRequestHandler {


    public ContactScreenHttpResponse returnSuccess() {
        return returnSuccess(ContactScreenErrorCodeEnumConst.SUCCESS.getMsg());
    }

    public ContactScreenHttpResponse returnSuccess(String successMsg) {
        return returnSuccess(null, successMsg);
    }

    public ContactScreenHttpResponse returnSuccess(Object object) {
        return returnSuccess(object, ContactScreenErrorCodeEnumConst.SUCCESS.getMsg());
    }

    public ContactScreenHttpResponse returnSuccess(Object object, String successMsg) {
        ContactScreenHttpResponse response = new ContactScreenHttpResponse();
        response.setCode(ContactScreenErrorCodeEnumConst.SUCCESS.getCode());
        response.setMessage(successMsg);
        response.setResult(object);
        return response;
    }


}
