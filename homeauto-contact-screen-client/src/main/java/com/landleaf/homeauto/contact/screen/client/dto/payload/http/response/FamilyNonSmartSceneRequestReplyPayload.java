package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyNonSmartScene;
import lombok.Data;

import java.util.List;

/**
 * 场景(自由方舟)请求
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartSceneRequestReplyPayload {

    /**
     * 场景集
     */
   List<ContactScreenFamilyNonSmartScene> data;




}
