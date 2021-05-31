package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductCategoryService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysProductContorller
 * @Description: TODO
 * @Author shizg
 * @Date 2021/5/24
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/sys-produt/")
@Api(value = "/web/sys-produt/", tags = {"系统产品配置"})
public class SysProductContorller extends BaseController {
    @Autowired
    private ISysProductService iSysProductService;
    @Autowired
    private ISysProductCategoryService iSysProductCategoryService;

    @ApiOperation(value = "新增系统产品")
    @PostMapping("add")
    public Response<Long> addSysProdut(@RequestBody SysProductDTO requestDTO){
        Long sysPid = iSysProductService.addSysProduct(requestDTO);
        return returnSuccess(sysPid);
    }

    @ApiOperation(value = "修改系统产品")
    @PostMapping("update")
    public Response updateSysProdut(@RequestBody SysProductDTO requestDTO){
        iSysProductService.updateSysProdut(requestDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "查看系统产品")
    @GetMapping("detail/{sysProductId}")
    public Response<SysProductDetailVO> detailSysProdut(@PathVariable("sysProductId") Long sysProductId){
        SysProductDetailVO productDetailVO = iSysProductService.getDetailSysProdut(sysProductId);
        return returnSuccess(productDetailVO);
    }

    @ApiOperation(value = "系统产品列表")
    @GetMapping("page")
    public Response<List<SysProductVO>> page(SysProductQryDTO request){
        List<SysProductVO> data  = iSysProductService.getList(request);
        return returnSuccess(data);
    }

    @ApiOperation(value = "根据系统产品id删除系统产品")
    @DeleteMapping("delete/{sysProductId}")
    public Response deleteSysProdut(@PathVariable("sysProductId") Long sysProductId){
        iSysProductService.deleteSysProdutById(sysProductId);
        return returnSuccess();
    }

    @ApiOperation(value = "启停用系统产品")
    @PostMapping("enable/switch")
    public Response enableSwitch(@RequestBody SysProductStatusDTO request){
        iSysProductService.enableSwitch(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取系统产品关联的品类列表")
    @GetMapping("category/list/{sysPid}")
    public Response<List<SelectedVO>> getListCategoryBySysPid(@PathVariable("sysPid") Long sysPid){
        List<SelectedVO> data = iSysProductCategoryService.getListCategoryBySysPid(sysPid);
        return returnSuccess(data);
    }

    @ApiOperation(value = "新增系统设备时获取品类下的产品下拉列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/products/{categoryCode}")
    public Response<List<ProductInfoSelectVO>> getListProductSelectByCategoryCode(@PathVariable("categoryCode" )String categoryCode){
        List<ProductInfoSelectVO> result = iSysProductService.getListProductSelectByCategoryCode(categoryCode);
        return returnSuccess(result);
    }

}
