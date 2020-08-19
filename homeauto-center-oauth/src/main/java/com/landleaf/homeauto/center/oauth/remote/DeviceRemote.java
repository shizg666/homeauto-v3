package com.landleaf.homeauto.center.oauth.remote;

import com.landleaf.homeauto.common.domain.vo.oauth.FamilyVO;
import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wenyilu
 */
@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_DEVICE)
public interface DeviceRemote {


    @ApiOperation("获取家庭列表")
    @PostMapping("/device/smart/family")
    public Response<FamilyVO> getFamily(@RequestParam("userId") String userId);

}
