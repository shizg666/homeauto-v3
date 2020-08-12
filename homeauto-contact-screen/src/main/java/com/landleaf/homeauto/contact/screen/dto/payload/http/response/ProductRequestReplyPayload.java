package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenNews;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenProduct;
import lombok.Data;

import java.util.List;

/**
 * 产品信息请求响应
 *
 * @author wenyilu
 */
@Data
public class ProductRequestReplyPayload {

    /**
     * 产品集
     */
    private List<ContactScreenProduct> data;
}

