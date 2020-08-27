package com.landleaf.homeauto.center.device.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_OAUTH)
public interface UserRemote {

    @GetMapping(value = "/auth/sys-role-permission-scop/paths")
    public Response<List<String>> getUserPaths(@RequestParam("userId") String userId);


    @PostMapping(value = "/auth/customer/web/list/ids")
    public Response<List<HomeAutoCustomerDTO>> getListByIds(@RequestBody List<String> userIds);

}
