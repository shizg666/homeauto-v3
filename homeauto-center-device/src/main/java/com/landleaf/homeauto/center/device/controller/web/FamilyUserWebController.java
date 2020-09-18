package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.family.familyUerRemoveDTO;
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
@RequestMapping("/web/family-user/")
@Api(value = "/web/family-user/", tags = {"家庭组配置接口"})
public class FamilyUserWebController extends BaseController {

    @Autowired
    private IFamilyUserService iFamilyUserService;


    @ApiOperation(value = "用户解绑家庭", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("remove-user")
    public Response removeUser(@RequestBody familyUerRemoveDTO request){
        iFamilyUserService.removeUser(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取家庭成员类型列表", notes = "查询", consumes = "application/json")
    @GetMapping(value = "types")
    public Response<List<SelectedIntegerVO>> getMenberTypes() {
        List<SelectedIntegerVO> result =  iFamilyUserService.getMenberTypes();
        return returnSuccess(result);
    }

        @ApiOperation(value = "添加家庭成员", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add")
    public Response<String> add(@RequestBody @Valid FamilyUserDTO request) {
        iFamilyUserService.addMember(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除成员", notes = "删除", consumes = "application/json")
    @PostMapping("/delete/{id}")
    public Response delete(@PathVariable("id") String id ) {
        iFamilyUserService.deleteById(id);
        return returnSuccess();
    }

    @ApiOperation(value = "设置为管理员", notes = "", consumes = "application/json")
    @PostMapping("/setting/admin")
    public Response settingAdmin(@RequestBody FamilyUserOperateDTO request) {
        iFamilyUserService.settingAdmin(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取家庭组信息列表", notes = "查询", consumes = "application/json")
    @GetMapping(value = "/list/{familyId}")
    public Response<List<FamilyUserPageVO>> getListFamilyMember(@PathVariable("familyId") String familyId) {
        List<FamilyUserPageVO> result =  iFamilyUserService.getListFamilyMember(familyId);
        return returnSuccess(result);
    }




}
