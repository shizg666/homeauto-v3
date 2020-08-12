package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpProductResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenProduct;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.ProductRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ProductRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<ProductRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {

        ProductRequestReplyPayload result = new ProductRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setFamilyCode(header.getFamilyCode());
        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpProductResponseDTO>> responseDTO = adapterClient.getProductList(requestDTO);

        List<ScreenHttpProductResponseDTO> tmpResult = responseDTO.getResult();
        List<ContactScreenProduct> data = tmpResult.stream().map(i -> {
            ContactScreenProduct screenProduct = new ContactScreenProduct();
            BeanUtils.copyProperties(i, screenProduct);
            return screenProduct;

        }).collect(Collectors.toList());
        result.setData(data);
        return returnSuccess(result);

    }


}