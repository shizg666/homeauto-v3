package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpNewsResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.NewsResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息公告更新payload
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilyNewsRequestHandle extends AbstractHttpRequestHandler {

    @Autowired
    private AdapterClient adapterClient;

    public ContactScreenHttpResponse<List<NewsResponsePayload>> handlerRequest(CommonHttpRequestPayload requestPayload) {

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());
        Response<List<ScreenHttpNewsResponseDTO>> responseDTO = null;
        try {
            responseDTO = adapterClient.getNews(requestDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (responseDTO != null && responseDTO.isSuccess()) {
            List<ScreenHttpNewsResponseDTO> tmpResult = responseDTO.getResult();
            if(!CollectionUtils.isEmpty(tmpResult)){
                List<NewsResponsePayload> data = tmpResult.stream().map(i -> {
                    NewsResponsePayload screenNews = new NewsResponsePayload();
                    BeanUtils.copyProperties(i, screenNews);
                    return screenNews;

                }).collect(Collectors.toList());
                return returnSuccess(data);
            }
        }

        return returnError();
    }


}