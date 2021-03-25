package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.dto.protocol.*;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.ISelectService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ProtocolController
 * @Description: TODO
 * @Author shizg
 * @Date 2020/12/25
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/select/")
@Api(value = "/web/select/", tags = {"公共下拉数据控制"})
public class SelectDataController extends BaseController {

    @Autowired
    private ISelectService iSelectService;


    @ApiOperation(value = "获取协议类型下拉列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("protocol/type")
    public Response<List<SelectedIntegerVO>> listType(){
        List<SelectedIntegerVO> types = iSelectService.getListSelectProtocolType();
        return returnSuccess(types);
    }

    @ApiOperation(value = "获取协议属性类型下拉列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("protocol-attr/type")
    public Response<List<SelectedIntegerVO>> listAttrType(){
        List<SelectedIntegerVO> types = iSelectService.getListSelectProtocolAttrType();
        return returnSuccess(types);
    }

    @ApiOperation(value = "获取协议下拉列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("protocol")
    public Response<List<SelectedVO>> getListSelectProtocol(@ApiParam("协议类型(不穿查全部类型)") @RequestParam(value = "type",required = false) Integer type){
        List<SelectedVO> result = iSelectService.getListSelectProtocol(type);
        return returnSuccess(result);
    }

    @ApiOperation(value = "品类下拉列表", notes = "品类下拉列表")
    @GetMapping("categorys")
    public Response<List<SelectedVO>> getCategorys(){
        List<SelectedVO> result = iSelectService.getListSelectCategory();
        return returnSuccess(result);
    }

//    @ApiOperation(value = "属性单位下拉列表", notes = "品类下拉列表")
//    @GetMapping("unit")
//    public Response<List<SelectedVO>> getUnits(){
//        List<SelectedVO> result = iSelectService.getListSelectUnit();
//        return returnSuccess(result);
//    }

    @ApiOperation(value = "项目户型下拉列表", notes = "项目户型下拉列表")
    @GetMapping("template/{projectId}")
    public Response<List<SelectedVO>> getListSelectTemplates(@PathVariable("projectId")String projectId){
        List<SelectedVO> result = iSelectService.getListSelectTemplates(projectId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "家庭设备下拉列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("/device/{familyId}")
    public Response<List<SelectedVO>> getSelectByFamilyId(@PathVariable("familyId")String familyId){
        List<SelectedVO> result = iSelectService.getSelectByFamilyId(familyId);
        return returnSuccess(result);
    }






}
