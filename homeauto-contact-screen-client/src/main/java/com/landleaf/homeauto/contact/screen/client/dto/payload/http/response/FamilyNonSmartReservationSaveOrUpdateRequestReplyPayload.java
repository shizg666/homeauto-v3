package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenFamilyNonSmartReservation;
import lombok.Data;

import java.util.List;

/**
 * 自由方舟预约新增修改请求响应payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartReservationSaveOrUpdateRequestReplyPayload {


    /**
     * 修改或新增数据集
     */
    List<ContactScreenFamilyNonSmartReservation> data;



}
