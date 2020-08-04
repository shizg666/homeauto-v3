package com.landleaf.homeauto.contact.screen.http.handle.event;

import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenResponse;
import com.landleaf.homeauto.common.domain.dto.screen.payload.event.ContactScreenFamilyEventAlarmPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.event.ContactScreenFamilyEventReplyPayload;
import com.landleaf.homeauto.contact.screen.http.handle.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 大屏报警事件上传请求处理
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ContactScreenFamilyEventAlarmHandle extends AbstractRequestHandler {


    public ContactScreenResponse<ContactScreenFamilyEventReplyPayload> handlerRequest(ContactScreenFamilyEventAlarmPayload payload) {



        return null;

    }

}
