package com.landleaf.homeauto.contact.screen.client.dto.payload.http.request;

import lombok.Data;

/**
 * 自由方舟预约删除请求payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartReservationDeleteRequestPayload {
    /**
     * 预约ID
     */
    private String reservationId;
}
