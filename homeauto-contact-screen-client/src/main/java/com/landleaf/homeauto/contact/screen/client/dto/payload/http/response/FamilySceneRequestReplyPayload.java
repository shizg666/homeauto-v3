package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyScene;
import lombok.Data;

import java.util.List;

/**
 * 场景(户式化)请求
 *
 * @author wenyilu
 */
@Data
public class FamilySceneRequestReplyPayload {

    /**
     * 场景集
     */
    List<ContactScreenFamilyScene> data;



}
