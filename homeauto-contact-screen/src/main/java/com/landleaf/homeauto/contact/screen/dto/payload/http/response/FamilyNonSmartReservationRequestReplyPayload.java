package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyNonSmartReservation;
import lombok.Data;

import java.util.List;

/**
 * 自由方舟预约配置信息请求响应payload
 *
 * @author wenyilu
 */
@Data
public class FamilyNonSmartReservationRequestReplyPayload {

    List<ContactScreenFamilyNonSmartReservation> data;



}
