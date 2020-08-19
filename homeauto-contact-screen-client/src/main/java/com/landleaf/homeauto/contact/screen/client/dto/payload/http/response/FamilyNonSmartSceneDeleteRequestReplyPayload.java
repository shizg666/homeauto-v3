package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyScene;
import lombok.Data;

import java.util.List;

/**
 * 场景(自由方舟)删除请求响应payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartSceneDeleteRequestReplyPayload {


    /**
     * 场景集
     */
    List<ContactScreenFamilyScene> data;

}
