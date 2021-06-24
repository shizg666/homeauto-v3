package com.landleaf.homeauto.center.data.controller;

import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Api(value = "状态接口", tags = "状态查询接口")
public class StatusController extends BaseController {

    @Autowired
    private IFamilyDeviceStatusHistoryService historyService;


    @PostMapping("/status/history")
    @ApiOperation("获取家庭设备历史数据")
    Response<BasePageVO<FamilyDeviceStatusHistory>> getStatusHistory(@RequestBody HistoryQryDTO2 historyQryDTO){
        System.out.println(historyQryDTO.toString());
        BasePageVO<FamilyDeviceStatusHistory> list =  historyService.getStatusByFamily(historyQryDTO);
        return returnSuccess(list);
    }

}
