package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
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
 * 暖通故障控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/fault/havc")
@Api(value = "/fault/havc", tags = {"暖通故障请求接口"})
public class HomeAutoFaultDeviceHavcController extends BaseController {

    @Autowired
    private IHomeAutoFaultDeviceHavcService homeAutoFaultDeviceHavcService;
    @PostMapping("/batch/save")
    public Response<Void>  batchSave(@RequestBody List<HomeAutoFaultDeviceHavcDTO> data){

        homeAutoFaultDeviceHavcService.batchSave(data);
       return returnSuccess();
    }

}
