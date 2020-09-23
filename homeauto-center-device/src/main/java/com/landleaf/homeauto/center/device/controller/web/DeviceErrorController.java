package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceErrorService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/web/device/error/")
@Api(value = "/web/device/error/", tags = {"设备故障接口"})
public class DeviceErrorController extends BaseController {

    @Autowired
    private IDeviceErrorService iDeviceErrorService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;



    @ApiOperation(value = "设备故障信息获取", consumes = "application/json")
    @PostMapping(value = "get/list/error")
    @ResponseBody
    public Response<BasePageVO<DeviceErrorVO>> getListDeviceError(@RequestBody  DeviceErrorQryDTO request) {
        BasePageVO<DeviceErrorVO> result = iDeviceErrorService.getListDeviceError(request);
        return returnSuccess(result);
    }


    @ApiOperation(value = "批量更新状态", consumes = "application/json")
    @PostMapping(value = "update/status")
    @ResponseBody
    public Response updateBatchStatus(@RequestBody DeviceErrorUpdateDTO request) {
        iDeviceErrorService.updateBatchStatus(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取产品下拉选择", consumes = "application/json")
    @PostMapping(value = "get/products")
    @ResponseBody
    public Response<List<SelectedVO>> getListProduct() {
        List<SelectedVO> result = iHomeAutoProductService.getListCodeSelects();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取家庭下拉选择", consumes = "application/json")
    @PostMapping(value = "get/familys")
    @ResponseBody
    public Response<List<SelectedVO>> getListFamily() {
        List<SelectedVO> result = iHomeAutoFamilyService.getListFamilySelects();
        return returnSuccess(result);
    }

    @ApiOperation(value = "导出数据", consumes = "application/json")
    @PostMapping(value = "export/list/error")
    public Response<BasePageVO<DeviceErrorVO>> exportListDeviceError(@RequestBody  DeviceErrorQryDTO request, HttpServletResponse response) {
        iDeviceErrorService.exportListDeviceError(request,response);
        return returnSuccess();
    }




}
