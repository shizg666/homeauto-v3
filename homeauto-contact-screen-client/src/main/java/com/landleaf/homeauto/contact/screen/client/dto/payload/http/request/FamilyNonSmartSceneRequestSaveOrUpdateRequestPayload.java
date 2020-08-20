package com.landleaf.homeauto.contact.screen.client.dto.payload.http.request;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyScene;
import lombok.Data;

import java.util.List;

/**
 * 场景(自由方舟) 新增修改请求payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartSceneRequestSaveOrUpdateRequestPayload {

    /**
     * 场景集
     */
    List<ContactScreenFamilyScene> request;


}
