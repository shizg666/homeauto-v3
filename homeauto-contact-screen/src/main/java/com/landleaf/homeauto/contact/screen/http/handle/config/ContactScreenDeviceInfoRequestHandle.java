package com.landleaf.homeauto.contact.screen.http.handle.config;

import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenResponse;
import com.landleaf.homeauto.common.domain.dto.screen.payload.request.config.ContactScreenFamilyConfigRequestPayload;
import com.landleaf.homeauto.contact.screen.http.handle.AbstractRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 设备信息请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ContactScreenDeviceInfoRequestHandle extends AbstractRequestHandler {


    public ContactScreenResponse handlerRequest(ContactScreenFamilyConfigRequestPayload payload) {

        // 通过内部服务调用adapter,adapter调用具体服务

        return null;

    }


}

