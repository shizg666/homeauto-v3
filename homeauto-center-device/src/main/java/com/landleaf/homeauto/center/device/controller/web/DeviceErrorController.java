package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.service.mybatis.AddressService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/web/device/error/")
@Api(value = "/web/device/error/", tags = {"设备故障接口"})
public class DeviceErrorController extends BaseController {

    @Autowired
    private IHomeAutoFaultDeviceHavcService iHomeAutoFaultDeviceHavcService;

    @Autowired
    private IHomeAutoFaultDeviceValueService iHomeAutoFaultDeviceValueService;


    @Autowired
    private IHomeAutoFaultDeviceLinkService iHomeAutoFaultDeviceLinkService;



    @ApiOperation(value = "设备故障信息获取", consumes = "application/json")
    @PostMapping(value = "get/list/error")
    public Response<List<DeviceErrorVO>> getListDeviceError(@RequestBody DeviceErrorQryDTO request) {
        if (request.getType() == null){
            return returnSuccess();
        }
        List<DeviceErrorVO> result = null;
        if (AttributeErrorTypeEnum.ERROR_CODE.getType().equals(request)){
            result = iHomeAutoFaultDeviceHavcService.getListDeviceError(request);
        }else if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(request)){
            result = iHomeAutoFaultDeviceLinkService.getListDeviceError(request);
        }else {
            result = iHomeAutoFaultDeviceValueService.getListDeviceError(request);
        }
        return returnSuccess(result);
    }



}
