package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.project.HomeDeviceStatistics;
import com.landleaf.homeauto.center.device.model.vo.project.HomeDeviceStatisticsQry;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatistics;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatisticsQry;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IKanBanService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName HomeController
 * @Description: TODO
 * @Author shizg
 * @Date 2021/2/4
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/kanban/")
@Api(value = "/web/kanban/", tags = {"看板接口"})
public class KanbanController extends BaseController {

    @Autowired
    private IKanBanService iKanBanService;


    @ApiOperation(value = "看板", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("kanban")
    public Response<List<KanBanStatistics>> getKanbanStatistics(KanBanStatisticsQry request){
        List<KanBanStatistics> data = iKanBanService.getKanbanStatistics(request);
        return returnSuccess(data);
    }

}
