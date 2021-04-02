package com.landleaf.homeauto.contact.screen.client.dto.payload.http.request;

import lombok.Data;

import java.util.List;

/**
 * 场景(自由方舟) 删除请求payload
 *
 * @author wenyilu
 */
@Data
public class FamilySceneDeleteRequestPayload {

    /**
     * 场景ID
     */
    private List<String> request;


}
