package com.landleaf.homeauto.center.device.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping(value = "/auth/customer/web/userinfo")
    Response<CustomerInfoDTO> getCustomerInfoById(@RequestParam("userId") String userId);

    @PostMapping(value = "/auth/customer/web/list/ids")
    Response<List<HomeAutoCustomerDTO>> getCustomerInfoByIds(@RequestBody List<String> userIds);

    @ApiOperation(value = "客户绑定工程通知web端操作", notes = "客户绑定工程通知", consumes = "application/json")
    @GetMapping(value = "/auth/customer/web/bind/family")
    public Response bindFamilyNotice(@RequestParam("userId") String userId);

    @ApiOperation(value = "客户解绑工程减少通知web端操作", notes = "客户绑定工程通知", consumes = "application/json")
    @PostMapping(value = "/auth/customer/web/unbind/family")
    public Response unbindFamilyNotice(@RequestBody List<String> userIds);
}
