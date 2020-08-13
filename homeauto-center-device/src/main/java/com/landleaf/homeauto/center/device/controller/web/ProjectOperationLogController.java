package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IProjectOperationLogService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.project.ProjectOperationLogVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectLogQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.landleaf.homeauto.common.web.BaseController;

/**
 * <p>
 * 项目操作日志表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/project-operation-log/")
@Api(value = "/web/project-operation-log/", tags = {"项目配置日志接口"})
public class ProjectOperationLogController extends BaseController {


    @Autowired
    private IProjectOperationLogService iProjectOperationLogService;

    @ApiOperation(value = "分页查询", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<ProjectOperationLogVO>> page(@RequestBody ProjectLogQryDTO projectLogQryDTO){
        BasePageVO<ProjectOperationLogVO> result = iProjectOperationLogService.page(projectLogQryDTO);
        return returnSuccess(result);
    }

}
