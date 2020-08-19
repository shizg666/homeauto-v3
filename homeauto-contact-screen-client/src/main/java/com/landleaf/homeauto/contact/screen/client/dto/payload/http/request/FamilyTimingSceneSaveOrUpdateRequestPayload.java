package com.landleaf.homeauto.contact.screen.client.dto.payload.http.request;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyTimingScene;
import lombok.Data;

import java.util.List;

/**
 * 自由方舟预约新增修改请求payload
 *
 * @author wenyilu
 */
@Data
public class FamilyTimingSceneSaveOrUpdateRequestPayload {

    /**
     * 修改或新增数据集
     */
    List<ContactScreenFamilyTimingScene> data;
}
