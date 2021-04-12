package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
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

    @ApiOperation(value = "项目楼房管理列表", notes = "根据单元id查询家庭列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("project/total/{projectId}")
    public Response<List<ProjectFamilyTotalVO>> getProjectFamilyTotal(@PathVariable("projectId") Long projectId){
        List<ProjectFamilyTotalVO> result = iHomeAutoFamilyService.getProjectFamilyTotal(projectId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增家庭", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增家庭")
    public Response add(@RequestBody @Valid FamilyAddDTO request){
        iHomeAutoFamilyService.add(request);
        return returnSuccess();
    }

//    @ApiOperation(value = "修改家庭（修改id必传）", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update")
//    @LogAnnotation(name ="修改家庭")
//    public Response update(@RequestBody @Valid FamilyAddDTO request){
//        iHomeAutoFamilyService.update(request);
//        return returnSuccess();
//    }

    @ApiOperation(value = "修改家庭户型", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/templateId")
    public Response updateFamilyTempalteId(@RequestBody  FamilyTempalteUpdateDTO request){
        iHomeAutoFamilyService.updateFamilysTempalteId(request);
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

    @ApiOperation(value = "查询家庭列表", notes = "根据单元id查询家庭列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("list/page")
    public Response<BasePageVO<FamilyPageVO>> getListPage(@RequestBody FamilyQryDTO familyQryDTO){
        BasePageVO<FamilyPageVO> result = iHomeAutoFamilyService.getListPage(familyQryDTO);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据项目id获取户型下拉列表", notes = "根据项目id获取户型下拉列表")
    @GetMapping("list/tempalte/{projectId}")
    public Response<List<TemplateSelectedVO>> getListTemplateSelect(@PathVariable("projectId") String projectId){
        List<TemplateSelectedVO> result = iProjectHouseTemplateService.getListSelectByProjectId(projectId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查询用户家庭列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("list/user/{userId}")
    public Response<List<FamilyUserVO>> getListByUser(@PathVariable("userId") String userId){
        List<FamilyUserVO> familyUserVOS = iHomeAutoFamilyService.getListByUser(userId);
        return returnSuccess(familyUserVOS);
    }

    @ApiOperation(value = "启停用状态下拉列表", notes = "")
    @GetMapping("enableStatus")
    public Response<List<SelectedIntegerVO>> getEnableStatus(){
        List<SelectedIntegerVO> result = iHomeAutoFamilyService.getEnableStatus();
        return returnSuccess(result);
    }


    @ApiOperation(value = "家庭下拉列表（根据项目id）")
    @GetMapping("select/{projectId}")
    public  Response<List<SelectedVO>> getSelectsFamilyByProjectId(@PathVariable("projectId") String projectId){
        List<SelectedVO> data = iHomeAutoFamilyService.getListFamilySelects(projectId);
        return returnSuccess(data);
    }

    @ApiOperation(value = "查看家庭基本信息")
    @GetMapping("get/base-info/{familyId}")
    public  Response<FamilyBaseInfoVO> getfamilyBaseInfoById(@PathVariable("familyId") String familyId){
        FamilyBaseInfoVO data = iHomeAutoFamilyService.getfamilyBaseInfoById(familyId);
        return returnSuccess(data);
    }

    @ApiOperation(value = "根据家庭id获取设备列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/device/list/{familyId}")
    public Response<BasePageVO<TemplateDevicePageVO>> getListDeviceByFamilyId(@PathVariable("familyId") String familyId,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum){
        BasePageVO<TemplateDevicePageVO> result = iHomeAutoFamilyService.getListDeviceByFamilyId(familyId,pageSize,pageNum);
        return returnSuccess(result);
    }

}
