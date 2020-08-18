package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
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
 * 产品表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/product/")
@Api(value = "/web/product/", tags = {"产品接口"})
public class HomeAutoProductController extends BaseController {

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;


    @ApiOperation(value = "新增/修改产品（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response add(@RequestBody @Valid ProductDTO request){
        if(StringUtil.isEmpty(request.getId())){
            iHomeAutoProductService.add(request);
        }else {
            iHomeAutoProductService.update(request);
        }
        return returnSuccess();
    }



    @ApiOperation(value = "删除产品", notes = "删除产品")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response delete(@PathVariable("id") String id){
        iHomeAutoProductService.delete(id);
        return returnSuccess();
    }


    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<ProductPageVO>> page(@RequestBody ProductQryDTO request){
        BasePageVO<ProductPageVO> result = iHomeAutoProductService.page(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取协议下拉列表", notes = "获取协议下拉列表")
    @GetMapping("get/protocols")
    public Response<List<SelectedVO>> getProtocols(){
        List<SelectedVO> result = iHomeAutoProductService.getProtocols();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取波特率下拉列表", notes = "获取波特率下拉列表")
    @GetMapping("get/baudRates")
    public Response<List<SelectedIntegerVO>> getBaudRates(){
        List<SelectedIntegerVO> result = iHomeAutoProductService.getBaudRates();
        return returnSuccess(result);
    }


    @ApiOperation(value = "获取校验模式下拉列表", notes = "获取校验模式下拉列表")
    @GetMapping("get/checkModes")
    public Response<List<SelectedIntegerVO>> getCheckModes(){
        List<SelectedIntegerVO> result = iHomeAutoProductService.getCheckModes();
        return returnSuccess(result);
    }

    @ApiOperation(value = "修改产品 根据产品id获取产品属性信息", notes = "获取校验模式下拉列表")
    @PostMapping("get/product/attributes/{id}")
    public Response<List<ProductAttributeVO>> getProductAttributeInfo(@PathVariable("id") String id){
        List<ProductAttributeVO> result = iHomeAutoProductService.getListAttributeById(id);
        return returnSuccess(result);
    }

}
