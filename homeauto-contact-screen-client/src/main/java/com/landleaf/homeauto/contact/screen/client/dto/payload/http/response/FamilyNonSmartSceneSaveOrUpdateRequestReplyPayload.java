package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyNonSmartScene;
import lombok.Data;

import java.util.List;

/**
 * 场景(自由方舟) 新增修改请求响应payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartSceneSaveOrUpdateRequestReplyPayload {

    /**
     * 场景集
     */
    List<ContactScreenFamilyNonSmartScene> data;




}
