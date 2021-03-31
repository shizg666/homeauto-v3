package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeDicService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
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
 * 属性字典表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/attribute-dic/")
@Api(value = "/web/attribute-dic/", tags = {"功能属性池"})
public class AttribureDicController extends BaseController {

    @Autowired
    private IHomeAutoAttributeDicService iHomeAutoAttribureDicService;

    @ApiOperation(value = "新增/修改属性（修改id必传）", notes = "新增属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("addOrUpdate")
    public Response add(@RequestBody @Valid AttributeDicDTO request) {
        if (Objects.isNull(request.getId())) {
            iHomeAutoAttribureDicService.add(request);
        } else {
            iHomeAutoAttribureDicService.update(request);
        }
        return returnSuccess();
    }


    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("page")
    public Response<BasePageVO<AttributeDicPageVO>> page(@RequestBody AttributeDicQryDTO request) {
        BasePageVO<AttributeDicPageVO> result = iHomeAutoAttribureDicService.pageList(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看属性", notes = "查看属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @GetMapping("get/detail/{id}")
    public Response<AttributeDicDetailVO> getDetailById(@PathVariable("id") String id) {
        AttributeDicDetailVO result = iHomeAutoAttribureDicService.getDetailById(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "删除属性", notes = "删除属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("delete/{id}")
    public Response deleteById(@PathVariable("id") String id) {
        iHomeAutoAttribureDicService.deleteById(id);
        return returnSuccess();
    }

    @ApiOperation(value = "获取属性类别下拉列表", notes = "获取属性类别下拉列表")
    @GetMapping("get/types")
    public Response<List<SelectedIntegerVO>> getAttributeDicTypes() {
        List<SelectedIntegerVO> result = iHomeAutoAttribureDicService.getAttributeDicTypes();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取属性性质下拉列表", notes = "获取属性性质下拉列表")
    @GetMapping("get/natures")
    public Response<List<SelectedIntegerVO>> getAttributeDicNatures() {
        List<SelectedIntegerVO> result = iHomeAutoAttribureDicService.getAttributeDicNatures();
        return returnSuccess(result);
    }


    @ApiOperation(value = "功能属性池名称下拉列表", notes = "产品功能属性名称下拉列表")
    @GetMapping("get/attributes")
    public Response<List<SelectedVO>> getAttributes() {
        List<SelectedVO> result = iHomeAutoAttribureDicService.getAttributes();
        return returnSuccess(result);
    }


    @ApiOperation(value = "根据属性code获取属性的级联信息", notes = "根据属性id获取属性的级联信息")
    @GetMapping("get/cascade-info/{code}")
    public Response<AttributeCascadeVO> getCascadeInfoByCode(@PathVariable("code") String code) {
        AttributeCascadeVO result = iHomeAutoAttribureDicService.getCascadeInfoByCode(code);
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增品类时获取所有属性字典列表", notes = "获取所有属性字典列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @GetMapping("get/attributes-list")
    public Response<List<AttributeDicVO>> getListAttributes() {
        List<AttributeDicVO> result = iHomeAutoAttribureDicService.getListAttributes();
        return returnSuccess(result);
    }


}
