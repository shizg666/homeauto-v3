package com.landleaf.homeauto.contact.screen.handle.http;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpNewsResponseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenNews;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.CommonHttpRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.NewsRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.controller.inner.remote.AdapterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public ContactScreenHttpResponse<NewsRequestReplyPayload> handlerRequest(CommonHttpRequestPayload requestPayload) {
        NewsRequestReplyPayload result = new NewsRequestReplyPayload();

        ContactScreenHeader header = ContactScreenContext.getContext();

        ScreenHttpRequestDTO requestDTO = new ScreenHttpRequestDTO();

        requestDTO.setScreenMac(header.getScreenMac());

        Response<List<ScreenHttpNewsResponseDTO>> responseDTO = adapterClient.getNews(requestDTO);

        List<ScreenHttpNewsResponseDTO> tmpResult = responseDTO.getResult();
        List<ContactScreenNews> data = tmpResult.stream().map(i -> {
            ContactScreenNews screenNews = new ContactScreenNews();
            BeanUtils.copyProperties(i, screenNews);
            return screenNews;

        }).collect(Collectors.toList());
        result.setData(data);
        return returnSuccess(result);

    }


}