package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyNonSmartReservationDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpSaveOrUpdateNonSmartReservationDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpNonSmartSaveOrUpdateReservationResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyNonSmartReservation;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilyNonSmartReservationSaveOrUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilyNonSmartReservationSaveOrUpdateRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约（自由方舟）新增、修改请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyNonSmartReservationSaveOrUpdateRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<FamilyNonSmartReservationSaveOrUpdateRequestReplyPayload> handlerRequest(FamilyNonSmartReservationSaveOrUpdateRequestPayload requestPayload) {
        FamilyNonSmartReservationSaveOrUpdateRequestReplyPayload result = new FamilyNonSmartReservationSaveOrUpdateRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpSaveOrUpdateNonSmartReservationDTO requestDTO = new ScreenHttpSaveOrUpdateNonSmartReservationDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        requestDTO.setData(requestPayload.getData().stream().map(i -> {
            ScreenFamilyNonSmartReservationDTO reservationDTO = new ScreenFamilyNonSmartReservationDTO();
            BeanUtils.copyProperties(i, reservationDTO);
            return reservationDTO;
        }).collect(Collectors.toList()));

        Response<List<ScreenHttpNonSmartSaveOrUpdateReservationResponseDTO>> responseDTO = adapterClient.saveOrUpdateNonSmartReservation(requestDTO);


        List<ScreenHttpNonSmartSaveOrUpdateReservationResponseDTO> tmpResult = responseDTO.getResult();

        List<ContactScreenFamilyNonSmartReservation> data = tmpResult.stream().map(i -> {
            ContactScreenFamilyNonSmartReservation payload = new ContactScreenFamilyNonSmartReservation();
            BeanUtils.copyProperties(i, payload);
            return payload;

        }).collect(Collectors.toList());
        result.setData(data);
        return returnSuccess(result);

    }


}