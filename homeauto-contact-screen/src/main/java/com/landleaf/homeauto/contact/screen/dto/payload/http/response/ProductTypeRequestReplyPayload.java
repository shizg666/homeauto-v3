package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenProductType;
import lombok.Data;

import java.util.List;

/**
 * 产品品类信息请求响应
 *
 * @author wenyilu
 */
@Data
public class ProductTypeRequestReplyPayload {

    /**
     * 产品集
     */
    private List<ContactScreenProductType> data;

}
