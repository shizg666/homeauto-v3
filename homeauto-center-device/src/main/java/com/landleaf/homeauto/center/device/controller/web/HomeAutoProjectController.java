package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProjectService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.landleaf.homeauto.common.web.BaseController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/project/")
@Api(value = "/web/project/", tags = {"项目模块"})
public class HomeAutoProjectController extends BaseController {

    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;



    @ApiOperation(value = "新增/修改项目（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response addOrUpdate(@RequestBody @Valid ProjectDTO request){
        if (StringUtil.isEmpty(request.getId())){
            iHomeAutoProjectService.add(request);
        }else {
            iHomeAutoProjectService.update(request);
        }
        return returnSuccess();
    }

    @ApiOperation(value = "删除", notes = "新增属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response addOrUpdate(@PathVariable("id") String id){
        iHomeAutoProjectService.deleteById(id);
        return returnSuccess();
    }

    @ApiOperation(value = "分页查询", notes = "根据id获取楼盘信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<ProjectVO>> page(@RequestBody ProjectQryDTO request){
        BasePageVO<ProjectVO> result = iHomeAutoProjectService.page(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "项目状态切换", notes = "项目状态切换")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("status-switch")
    public Response statusSwitch(@RequestBody ProjectStatusDTO projectStatusDTO){
        iHomeAutoProjectService.statusSwitch(projectStatusDTO);
        return returnSuccess();
    }

//    @ApiOperation(value = "根据楼盘id获取项目列表", notes = "根据楼盘id获取项目列表")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("get/list/{id}")
//    public Response<List<ProjectVO>> getlistProjectsById(@PathVariable("id") String id){
//        List<ProjectVO> result = iHomeAutoProjectService.getlistProjectsById(id);
//        return returnSuccess(result);
//    }


    @ApiOperation(value = "项目类型列表", notes = "项目类型")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("types")
    public Response<List<SelectedIntegerVO>> types(){
        List<SelectedIntegerVO> result = iHomeAutoProjectService.types();
        return returnSuccess(result);
    }


    @ApiOperation(value = "项目下拉列表（带层级地址）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/projects")
    public Response<List<CascadeVo>> getListProjects(){
        List<CascadeVo> result = iHomeAutoProjectService.getListPathProjects();
        return returnSuccess(result);
    }

    @ApiOperation(value = "项目下拉列表（根据用户权限配置）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/filter/projects")
    public Response<List<SelectedVO>> getListSeclects(){
        List<SelectedVO> result = iHomeAutoProjectService.getListSeclects();
        return returnSuccess(result);
    }

    @ApiOperation(value = "楼盘项目下拉列表（根据用户权限过滤）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/filter/cascadeSeclects")
    public Response<List<CascadeVo>> getListCascadeSeclects(){
        List<CascadeVo> result = iHomeAutoProjectService.getListCascadeSeclects();
        return returnSuccess(result);
    }







}
