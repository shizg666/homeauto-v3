package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.util.StringUtil;
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
 * 品类表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/category/")
@Api(value = "/web/attribute-dic/", tags = {"产品品类接口"})
public class HomeAutoCategoryController extends BaseController {

    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;


    @ApiOperation(value = "新增/修改类别（修改id必传）", notes = "新增类别")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response add(@RequestBody @Valid CategoryDTO request){
        if(StringUtil.isEmpty(request.getId())){
            iHomeAutoCategoryService.add(request);
        }else {
            iHomeAutoCategoryService.update(request);
        }
        return returnSuccess();
    }

//    @ApiOperation(value = "修改类别", notes = "修改类别")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update")
//    public Response update(@RequestBody @Valid CategoryDTO request){
//        iHomeAutoCategoryService.update(request);
//        return returnSuccess();
//    }

    @ApiOperation(value = "删除类别", notes = "删除类别")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response update(@PathVariable("id") String categoryId){
        iHomeAutoCategoryService.deleteById(categoryId);
        return returnSuccess();
    }


    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<CategoryPageVO>> page(@RequestBody CategoryQryDTO request){
        BasePageVO<CategoryPageVO>  result = iHomeAutoCategoryService.pageList(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据品类id主键查看品类详情", notes = "查看品类详情")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("detail/{id}")
    public Response<CategoryDetailVO> getDetailById(@PathVariable String id){
        CategoryDetailVO  result = iHomeAutoCategoryService.getDetailById(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取协议下拉列表", notes = "获取协议下拉列表")
    @GetMapping("get/protocols")
    public Response<List<SelectedIntegerVO>> getProtocols(){
        List<SelectedIntegerVO> result = iHomeAutoCategoryService.getProtocols();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取波特率下拉列表", notes = "获取波特率下拉列表")
    @GetMapping("get/baudRates")
    public Response<List<SelectedIntegerVO>> getBaudRates(){
        List<SelectedIntegerVO> result = iHomeAutoCategoryService.getBaudRates();
        return returnSuccess(result);
    }


    @ApiOperation(value = "获取校验模式下拉列表", notes = "获取校验模式下拉列表")
    @GetMapping("get/checkModes")
    public Response<List<SelectedIntegerVO>> getCheckModes(){
        List<SelectedIntegerVO> result = iHomeAutoCategoryService.getCheckModes();
        return returnSuccess(result);
    }

    @ApiOperation(value = "修改品类属性（尚未完成）", notes = "获取校验模式下拉列表")
    @PostMapping("get/attributeInfo")
    public Response<CategoryAttributeVO> getAttributeInfo(@RequestBody CategoryAttributeQryDTO request){
        CategoryAttributeVO result = iHomeAutoCategoryService.getAttributeInfo(request);
        return returnSuccess(result);
    }

}
