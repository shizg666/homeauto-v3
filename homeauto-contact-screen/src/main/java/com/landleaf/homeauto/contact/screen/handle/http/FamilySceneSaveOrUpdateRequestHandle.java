package com.landleaf.homeauto.contact.screen.handle.http;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.http.request.FamilySceneRequestSaveOrUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.http.response.FamilySceneSaveOrUpdateResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 场景（自由方舟）新增修改请求
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class FamilySceneSaveOrUpdateRequestHandle extends AbstractHttpRequestHandler {


    public ContactScreenHttpResponse<List<FamilySceneSaveOrUpdateResponsePayload>> handlerRequest(FamilySceneRequestSaveOrUpdateRequestPayload requestPayload) {


        List<FamilySceneSaveOrUpdateResponsePayload> result = Lists.newArrayList();

        ContactScreenHeader header = ContactScreenContext.getContext();


        return returnError(null);

    }


}