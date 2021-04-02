package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyTimingScene;
import lombok.Data;

import java.util.List;

/**
 * 场景定时配置(户式化)请求
 *
 * @author wenyilu
 */
@Data
public class FamilySceneTimingRequestReplyPayload {

    /**
     * 定时场景集
     */
    private List<ContactScreenFamilyTimingScene> data;


}
