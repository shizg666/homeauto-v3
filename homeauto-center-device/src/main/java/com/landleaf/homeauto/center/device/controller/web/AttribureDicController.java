package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttribureDicService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 属性字典表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/attribute-dic/")
@Api(value = "/web/attribute-dic/", tags = {"产品品类功能属性"})
public class AttribureDicController extends BaseController {

    @Autowired
    private IHomeAutoAttribureDicService iHomeAutoAttribureDicService;

    @ApiOperation(value = "新增/修改属性（修改id必传）", notes = "新增属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response add(@RequestBody @Valid AttributeDicDTO request){
        if (StringUtil.isEmpty(request.getId())){
            iHomeAutoAttribureDicService.add(request);
        }else {
            iHomeAutoAttribureDicService.update(request);
        }
        return returnSuccess();
    }

//    @ApiOperation(value = "修改属性", notes = "修改属性")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update")
//    public Response update(@RequestBody  @Valid AttributeDicDTO request){
//        iHomeAutoAttribureDicService.update(request);
//        return returnSuccess();
//    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<AttributeDicPageVO>> page(@RequestBody AttributeDicQryDTO request){
        BasePageVO<AttributeDicPageVO> result = iHomeAutoAttribureDicService.pageList(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看属性", notes = "查看属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/detail/{id}")
    public Response<AttributeDicDetailVO> getDetailById(@PathVariable("id") String id){
        AttributeDicDetailVO result= iHomeAutoAttribureDicService.getDetailById(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "删除属性", notes = "删除属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response deleteById(@PathVariable("id") String id ){
        iHomeAutoAttribureDicService.deleteById(id);
        return returnSuccess();
    }

    @ApiOperation(value = "获取属性类别下拉列表", notes = "获取属性类别下拉列表")
    @GetMapping("get/types")
    public Response<List<SelectedVO>> getAttributeDicTypes(){
        List<SelectedVO> result = iHomeAutoAttribureDicService.getAttributeDicTypes();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取属性性质下拉列表", notes = "获取属性性质下拉列表")
    @GetMapping("get/natures")
    public Response<List<SelectedVO>> getAttributeDicNatures(){
        List<SelectedVO> result = iHomeAutoAttribureDicService.getAttributeDicNatures();
        return returnSuccess(result);
    }


    @ApiOperation(value = "产品功能属性名称下拉列表", notes = "产品功能属性名称下拉列表")
    @GetMapping("get/attributes")
    public Response<List<SelectedVO>> getAttributes(){
        List<SelectedVO> result = iHomeAutoAttribureDicService.getAttributes();
        return returnSuccess(result);
    }


    @ApiOperation(value = "根据属性id获取属性的级联信息", notes = "根据属性id获取属性的级联信息")
    @GetMapping("get/cascade-info/{id}")
    public Response<AttributeCascadeVO> getCascadeInfoById(@PathVariable("id") String id){
        AttributeCascadeVO result = iHomeAutoAttribureDicService.getCascadeInfoByCode(id);
        return returnSuccess(result);
    }



}
