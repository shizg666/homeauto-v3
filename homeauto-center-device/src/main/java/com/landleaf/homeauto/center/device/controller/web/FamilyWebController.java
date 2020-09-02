package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 楼栋表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/family")
@Api(value = "/web/family/", tags = {"家庭配置接口"})
public class FamilyWebController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;

    @ApiOperation(value = "新增家庭", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增家庭")
    public Response add(@RequestBody @Valid FamilyAddDTO request){
        iHomeAutoFamilyService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改家庭（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改家庭")
    public Response update(@RequestBody @Valid FamilyUpdateDTO request){
        iHomeAutoFamilyService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除家庭", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除家庭")
    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
        iHomeAutoFamilyService.delete(request);
        return returnSuccess();
    }


    @ApiOperation(value = "查看家庭", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("detail/{familyId}")
    public Response<FamilyDetailVO> detail(@PathVariable("familyId") String familyId){
        FamilyDetailVO result = iHomeAutoFamilyService.detail(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据单元id查询家庭列表", notes = "根据单元id查询家庭列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list/{id}")
    public Response<List<FamilyPageVO>> getListByProjectId(@PathVariable("id") String id){
        List<FamilyPageVO> result = iHomeAutoFamilyService.getListByUnitId(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据项目id获取户型下拉列表", notes = "根据项目id获取户型下拉列表")
    @GetMapping("list/tempalte/{projectId}")
    public Response<List<TemplateSelectedVO>> getListTemplateSelect(@PathVariable("projectId") String projectId){
        List<TemplateSelectedVO> result = iProjectHouseTemplateService.getListSelectByProjectId(projectId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "审核家庭", notes = "查询", consumes = "application/json")
    @PostMapping(value = "/review")
    @LogAnnotation(name ="审核家庭")
    public Response review(@RequestBody FamilyOperateDTO request) {
        iHomeAutoFamilyService.review(request);
        return returnSuccess();
    }

    @ApiOperation(value = "交付家庭", notes = "查询", consumes = "application/json")
    @PostMapping(value = "/subimt")
    @LogAnnotation(name ="交付家庭")
    public Response submit(@RequestBody FamilyOperateDTO request) {
        iHomeAutoFamilyService.submit(request);
        return returnSuccess();
    }

    @ApiOperation(value = "查询用户家庭列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("list/user")
    public Response<List<FamilyUserVO>> getListByUser(){
        return returnSuccess();
    }






}
