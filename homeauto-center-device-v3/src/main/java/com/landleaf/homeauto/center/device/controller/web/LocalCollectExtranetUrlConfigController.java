package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.family.ExtranetUrlConfigDTO;
import com.landleaf.homeauto.center.device.service.mybatis.ILocalCollectExtranetUrlConfigService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 本地数采外网URL配置 前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/extranet-url-config/")
public class LocalCollectExtranetUrlConfigController extends BaseController {
    @Autowired
    private ILocalCollectExtranetUrlConfigService iLocalCollectExtranetUrlConfigService;

    @PostMapping("local-collect/config/add")
    public Response addLocalCollectConfig(@RequestBody @Valid ExtranetUrlConfigDTO request){
        iLocalCollectExtranetUrlConfigService.addConfig(request);
        return returnSuccess();
    }

    @PostMapping("local-collect/get/url/{realestateId}")
    public Response<String> getLocalCollectConfig(@PathVariable("realestateId")Long realestateId){
        String url = iLocalCollectExtranetUrlConfigService.getLocalCollectConfig(realestateId);
        return returnSuccess();
    }

}
