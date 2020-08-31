package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 设备通信故障控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/fault/link")
@Api(value = "/fault/link", tags = {"通信故障请求接口"})
public class HomeAutoFaultDeviceLinkController extends BaseController {

    @Autowired
    private IHomeAutoFaultDeviceLinkService homeAutoFaultDeviceLinkService;
    @PostMapping("/batch/save")
    public Response<Void> batchSave(@RequestBody List<HomeAutoFaultDeviceLinkDTO> data){

        homeAutoFaultDeviceLinkService.batchSave(data);
        return returnSuccess();
    }

}
