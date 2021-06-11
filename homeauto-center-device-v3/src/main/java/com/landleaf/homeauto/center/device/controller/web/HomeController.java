package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.project.HomeDeviceStatistics;
import com.landleaf.homeauto.center.device.model.vo.project.HomeDeviceStatisticsQry;
import com.landleaf.homeauto.center.device.model.vo.project.KanBanStatistics;
import com.landleaf.homeauto.center.device.model.vo.project.KanBanStatisticsQry;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IKanBanService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HomeController
 * @Description: TODO
 * @Author shizg
 * @Date 2021/2/4
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/home/")
@Api(value = "/web/home/", tags = {"首页接口"})
public class HomeController extends BaseController {

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IKanBanService iKanBanService;


    @ApiOperation(value = "首页统计", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("statistics")
    public Response<List<HomeDeviceStatistics>> getDeviceStatistics(HomeDeviceStatisticsQry request){
        List<HomeDeviceStatistics> data = iHouseTemplateDeviceService.getDeviceStatistics(request);
        return returnSuccess(data);
    }

    @ApiOperation(value = "看板", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("kanban")
    public Response<List<KanBanStatistics>> getKanbanStatistics(KanBanStatisticsQry request){
        List<KanBanStatistics> data = iKanBanService.getKanbanStatistics(request);
        return returnSuccess(data);
    }

}
