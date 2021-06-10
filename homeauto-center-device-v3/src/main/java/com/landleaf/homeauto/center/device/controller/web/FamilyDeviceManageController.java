package com.landleaf.homeauto.center.device.controller.web;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceDetailVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceQryDTO;
import com.landleaf.homeauto.center.device.service.IFamilyDeviceManageService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 家庭设备管理
 **/
@RestController
@RequestMapping("/web/family-device-manage")
@Api(value = "/web/family-device-manage", tags = {"家庭设备管理"})
public class FamilyDeviceManageController extends BaseController {

    @Autowired
    private IFamilyDeviceManageService familyDeviceManageService;

    @ApiOperation(value = "家庭设备分页查询", consumes = "application/json")
    @GetMapping("list")
    public Response<BasePageVO<FamilyDevicePageVO>> listFamilyDevicePage(FamilyDeviceQryDTO familyDeviceQryDTO) {
        BasePageVO<FamilyDevicePageVO> data = familyDeviceManageService.listFamilyDevicePage(familyDeviceQryDTO);
        return returnSuccess(data);
    }

    @ApiOperation(value = "家庭设备详情", consumes = "application/json")
    @GetMapping("device/tetail")
    public Response<FamilyDeviceDetailVO> listFamilyDevicePage(@RequestParam("familyId") Long familyId, @RequestParam("deviceId") Long deviceId) {
        FamilyDeviceDetailVO data = familyDeviceManageService.getFamilyDeviceDetail(familyId,deviceId);
        return returnSuccess(data);
    }


}
