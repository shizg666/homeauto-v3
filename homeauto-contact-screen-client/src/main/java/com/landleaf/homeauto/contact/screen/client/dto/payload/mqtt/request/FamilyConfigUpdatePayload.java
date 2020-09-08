package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * 数据更新通知payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class FamilyConfigUpdatePayload {

    /**
     * 更新操作类型 scene
     * {@link ContactScreenConfigUpdateTypeEnum}
     */
    private String updateType;

    public FamilyConfigUpdatePayload(String updateType) {
        this.updateType = updateType;
    }

    public FamilyConfigUpdatePayload() {
    }
}
