package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectSoftConfigService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectSoftConfigDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 项目软件配置表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/project-soft-config/")
@Api(value = "/web/project-soft-config/", tags = {"项目软件配置接口"})
public class ProjectSoftConfigController extends BaseController {

    @Autowired
    private IProjectSoftConfigService iProjectSoftConfigService;


    @ApiOperation(value = "新增软件配置", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增软件配置")
    public Response add(@RequestBody ProjectSoftConfigDTO request){
        iProjectSoftConfigService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改软件配置（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改软件配置")
    public Response update(@RequestBody ProjectSoftConfigDTO request){
        iProjectSoftConfigService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "根据项目id获取项目的配置信息", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("get/config/{id}")
    public Response<List<ProjectSoftConfigDTO>> getConfigByProjectId(@PathVariable("id") String id){
        List<ProjectSoftConfigDTO> result = iProjectSoftConfigService.getConfigByProjectId(id);
        return returnSuccess(result);
    }

}
