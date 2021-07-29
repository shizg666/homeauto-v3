package com.landleaf.homeauto.center.data.controller;

import com.landleaf.homeauto.center.data.domain.CurrentQryDTO;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusCurrent;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.service.IFamilyDevicePowerHistoryService;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusCurrentService;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.datacollect.SyncCloudDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sync")
@Api(value = "同步数据接口", tags = "同步数据接口")
public class SyncDataController extends BaseController {

    @Autowired
    private IFamilyDeviceStatusHistoryService historyService;

    @Autowired
    private IFamilyDeviceStatusCurrentService currentService;



    @PostMapping("status-current")
    @ApiOperation("同步设备当前数据")
    Response<Void> syncDeviceStatusCurrent(@RequestBody SyncCloudDTO data){
        currentService.syncDeviceStatusCurrent(data);
        return returnSuccess();
    }

    @PostMapping("status-history")
    @ApiOperation("同步设备历史数据")
    Response<Void> syncDeviceStatusHistory(@RequestBody SyncCloudDTO data){
        return returnSuccess();
    }

}
