package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticPageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticQryDTO;
import com.landleaf.homeauto.center.device.service.ISpaceManageService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间管理
 **/
@RestController
@RequestMapping("/web/space-manage")
@Api(value = "/web/space-manage/", tags = {"空间管理"})
public class SpaceManageController extends BaseController {

    @Autowired
    private ISpaceManageService spaceManageService;

    @ApiOperation(value = "空间管理统计查询", consumes = "application/json")
    @GetMapping("list")
    public Response<BasePageVO<SpaceManageStaticPageVO>> spaceManageStatistics(SpaceManageStaticQryDTO spaceManageStaticQryDTO) {

        BasePageVO<SpaceManageStaticPageVO> data = spaceManageService.spaceManageStatistics(spaceManageStaticQryDTO);
        return returnSuccess(data);
    }
}
