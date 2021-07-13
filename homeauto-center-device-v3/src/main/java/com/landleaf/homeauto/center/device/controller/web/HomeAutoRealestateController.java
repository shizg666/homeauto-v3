package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoRealestateService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.CascadeLongVo;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 楼盘表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/realestate/")
@Api(value = "/web/realestate/", tags = {"楼盘模块"})
public class HomeAutoRealestateController extends BaseController {
    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;



    @ApiOperation(value = "新增/修改（修改id必传）", notes = "新增")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response addOrUpdate(@RequestBody @Valid RealestateDTO request){
        if (Objects.isNull(request.getId())){
            iHomeAutoRealestateService.add(request);
        }else {
            iHomeAutoRealestateService.update(request);
        }
        return returnSuccess();
    }

    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response addOrUpdate(@PathVariable("id") Long id){
        iHomeAutoRealestateService.deleteById(id);
        return returnSuccess();
    }


    @ApiOperation(value = "分页查询", notes = "根据id获取楼盘信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<RealestateVO>> page(@RequestBody RealestateQryDTO request){
        BasePageVO<RealestateVO> result = iHomeAutoRealestateService.page(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "楼盘下拉列表获取", notes = "楼盘下拉列表获取")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list/selects")
    public Response<List<SelectedLongVO>> ListSelects(){
        List<SelectedLongVO> result = iHomeAutoRealestateService.ListSelects();
        return returnSuccess(result);
    }


    @ApiOperation(value = "根据楼盘id获取楼盘开发商和地址信息", notes = "根据楼盘id获取楼盘开发商和地址信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/developer-path/{id}")
    public Response<RealestateDeveloperVO> getDeveloperInfoById(@PathVariable("id" ) Long id){
        RealestateDeveloperVO result = iHomeAutoRealestateService.getDeveloperInfoById(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "楼盘下拉列表（根据用户权限配置）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/filter/realestates")
    public Response<List<SelectedLongVO>> getListSeclects(){
        List<SelectedLongVO> result = iHomeAutoRealestateService.getListSeclectsByUser();
        return returnSuccess(result);
    }

    @ApiOperation(value = "楼盘项目楼栋级联数据获取", notes = "", consumes = "application/json")
    @GetMapping(value = "cascadeList/all")
    public Response<List<CascadeLongVo>> cascadeRealestateProjectBuild(@RequestParam(value = "name",required = false) String name) {
        List<CascadeLongVo> vos = iHomeAutoRealestateService.cascadeRealestateProjectBuild(name);
        return returnSuccess(vos);
    }

    @ApiOperation(value = "获取某一楼盘项目楼栋单元家庭级联数据获取", notes = "", consumes = "application/json")
    @GetMapping(value = "cascadeList/family/all")
    public Response<List<CascadeLongVo>> cascadeRealestateProjectFamily(@RequestParam("realestateId") Long realestateId,@RequestParam("projectId") Long projectId) {
        List<CascadeLongVo> vos = iHomeAutoRealestateService.cascadeRealestateProjectFamily(realestateId,projectId);
        return returnSuccess(vos);
    }

    @ApiOperation(value = "获取某一楼盘楼栋单元 户号级联数据获取", notes = "", consumes = "application/json")
    @GetMapping(value = "cascadeList/family/room")
    public Response<List<CascadeStringVo>> cascadeRealestateFamily(@RequestParam("realestateId") Long realestateId) {
        List<CascadeStringVo> vos = iHomeAutoRealestateService.cascadeRealestateFamilyRoom(realestateId);
        return returnSuccess(vos);
    }


//    @ApiOperation(value = "楼盘模式状态列表查询", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("get/status/realestates")
//    public Response<BasePageVO<RealestateModeStatusVO>> getListSeclectsByProject(@RequestBody RealestateModeQryDTO request){
//        BasePageVO<RealestateModeStatusVO> result = iHomeAutoRealestateService.getListSeclectsByProject(request);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "更改楼盘模式", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update/status")
//    public Response updateModeStatus(@RequestBody RealestateModeUpdateVO request){
//        iHomeAutoRealestateService.updateModeStatus(request);
//        return returnSuccess();
//    }

//    @ApiOperation(value = "模式下拉列表", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping("get/mode/status")
//    public Response<List<SelectedVO>> getModeStatusSeclects(){
//        List<SelectedVO> result = iHomeAutoRealestateService.getModeStatusSeclects();
//        return returnSuccess(result);
//    }







}
