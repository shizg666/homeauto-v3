package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.service.IDestroyCustomerService;
import com.landleaf.homeauto.common.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DestroyCustomerService implements IDestroyCustomerService {

    /**
     * 销毁客户账号
     *
     * @param userId 客户账号ID
     * @return
     */
    @Override
    public Response destroyCustomer(String userId) {
        Response response = new Response();
        return response;
    }
}
