package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpProductTypeResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenProductType;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.ProductTypeRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品类别信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ProductTypeRequestHandle extends AbstractHttpRequestHandler {


    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<ProductTypeRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {


        ProductTypeRequestReplyPayload result = new ProductTypeRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpProductTypeResponseDTO>> responseDTO = adapterClient.getProductTypeList(requestDTO);

        List<ScreenHttpProductTypeResponseDTO> tmpResult = responseDTO.getResult();
        List<ContactScreenProductType> data = tmpResult.stream().map(i -> {
            ContactScreenProductType screenProductType = new ContactScreenProductType();
            BeanUtils.copyProperties(i, screenProductType);
            return screenProductType;

        }).collect(Collectors.toList());
        result.setData(data);
        return returnSuccess(result);

    }


}