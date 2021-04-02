package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
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
 * 数值故障控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/fault/value")
@Api(value = "/fault/value", tags = {"数值故障请求接口"})
public class HomeAutoFaultDeviceValueController extends BaseController {

    @Autowired
    private IHomeAutoFaultDeviceValueService homeAutoFaultDeviceValueService;

    @PostMapping("/batch/save")
    public Response<Void> batchSave(@RequestBody List<HomeAutoFaultDeviceValueDTO> data) {

        homeAutoFaultDeviceValueService.batchSave(data);
        return returnSuccess();
    }

}
