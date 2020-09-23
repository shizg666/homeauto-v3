package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceRepairGuideDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceRepairGuideService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 设备维修指南 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-23
 */
@RestController
@RequestMapping("/web/device/repair-guide/")
@Api(value = "/web/device/repair-guide/", tags = {"设备维修指南"})
public class DeviceRepairGuideController extends BaseController {

    @Autowired
    private IDeviceRepairGuideService iDeviceRepairGuideService;

    @ApiOperation(value = "分页", consumes = "application/json")
    @PostMapping("page")
    public Response<BasePageVO<DeviceGuideVO>> page(@RequestBody DeviceGuideQryDTO request ){
        BasePageVO<DeviceGuideVO> result = iDeviceRepairGuideService.page(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增/修改设备指南", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response addOrUpdate(@RequestBody DeviceRepairGuideDTO request){
        iDeviceRepairGuideService.addOrUpdate(request);
        return returnSuccess();
    }


    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response deleteById(@PathVariable("id") String id){
        iDeviceRepairGuideService.removeById(id);
        return returnSuccess();
    }

    @ApiOperation(value = "获取故障类型下拉选择", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("get/types")
    public Response<List<SelectedIntegerVO>> getTypes(){
        List<SelectedIntegerVO> result = iDeviceRepairGuideService.getTypes();
        return returnSuccess(result);
    }

}
