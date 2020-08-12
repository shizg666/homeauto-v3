package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpNonSmartReservationResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyNonSmartReservation;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyNonSmartReservationRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约（自由方舟）请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyNonSmartReservationRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<FamilyNonSmartReservationRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {
        FamilyNonSmartReservationRequestReplyPayload result = new FamilyNonSmartReservationRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setFamilyCode(header.getFamilyCode());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpNonSmartReservationResponseDTO>> responseDTO = adapterClient.getReservationList(requestDTO);

        List<ScreenHttpNonSmartReservationResponseDTO> tmpResult = responseDTO.getResult();
        List<ContactScreenFamilyNonSmartReservation> data = tmpResult.stream().map(i -> {
            ContactScreenFamilyNonSmartReservation reservation = new ContactScreenFamilyNonSmartReservation();
            BeanUtils.copyProperties(i, reservation);
            return reservation;

        }).collect(Collectors.toList());

        result.setData(data);

        return returnSuccess(result);

    }


}