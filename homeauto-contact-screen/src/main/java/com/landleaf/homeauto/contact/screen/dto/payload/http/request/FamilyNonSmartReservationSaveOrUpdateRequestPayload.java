package com.landleaf.homeauto.contact.screen.dto.payload.http.request;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyNonSmartReservation;
import lombok.Data;

import java.util.List;

/**
 * 自由方舟预约新增修改请求payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartReservationSaveOrUpdateRequestPayload {

    /**
     * 修改或新增数据集
     */
    List<ContactScreenFamilyNonSmartReservation> data;
}
