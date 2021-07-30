package com.landleaf.homeauto.center.gateway.web;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LocalDataCollectPingController
 * @Description: 数采ping云端
 * @Author shizg
 * @Date 2021/7/28
 * @Version V1.0
 **/
@RestController
@RequestMapping("cloud/")
public class LocalDataCollectPingController extends BaseController {

    @GetMapping("ping")
    public Response<Integer>  ping(){
        return returnSuccess(1);
    }
}
