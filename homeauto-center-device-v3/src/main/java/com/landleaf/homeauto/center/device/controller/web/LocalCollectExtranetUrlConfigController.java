package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.ExtranetUrlConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.ExtranetUrlConfigDTO;
import com.landleaf.homeauto.center.device.service.mybatis.ILocalCollectExtranetUrlConfigService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/web/msg/", tags = {"楼盘本地数采外网ip配置"})
@RestController
@RequestMapping("/extranet-url-config/")
public class LocalCollectExtranetUrlConfigController extends BaseController {
    @Autowired
    private ILocalCollectExtranetUrlConfigService iLocalCollectExtranetUrlConfigService;

    @ApiOperation(value = "新增楼盘本地数采ip")
    @PostMapping("local-collect/config/add")
    public Response addLocalCollectConfig(@RequestBody @Valid ExtranetUrlConfigDTO request){
        iLocalCollectExtranetUrlConfigService.addConfig(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取楼盘本地数采ip")
    @PostMapping("local-collect/get/url/{realestateId}")
    public Response<ExtranetUrlConfigVO> getLocalCollectConfig(@PathVariable("realestateId")Long realestateId){
        ExtranetUrlConfigVO url = iLocalCollectExtranetUrlConfigService.getLocalCollectConfig(realestateId);
        return returnSuccess(url);
    }

}
