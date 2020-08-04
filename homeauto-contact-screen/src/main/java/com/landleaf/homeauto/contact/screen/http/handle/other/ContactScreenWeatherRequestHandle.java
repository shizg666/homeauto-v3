package com.landleaf.homeauto.contact.screen.http.handle.other;

import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenResponse;
import com.landleaf.homeauto.common.domain.dto.screen.payload.request.other.ContactScreenTimeRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.http.handle.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 天气信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ContactScreenWeatherRequestHandle extends AbstractRequestHandler {


    public ContactScreenResponse handlerRequest(ContactScreenTimeRequestReplyPayload payload) {


        return null;

    }
}