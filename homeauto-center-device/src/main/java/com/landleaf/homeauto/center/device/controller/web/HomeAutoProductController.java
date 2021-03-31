package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.dto.product.ProductDTO;
import com.landleaf.homeauto.center.device.model.dto.product.ProductPageVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.ProductDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.ProductQryDTO;
import com.landleaf.homeauto.common.util.StringUtil;
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

    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;

    @Autowired
    private IProductAttributeErrorService iProductAttributeErrorService;


    @ApiOperation(value = "新增/修改产品（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response<HomeAutoProduct> addOrUpdate(@RequestBody @Valid ProductDTO request){
        HomeAutoProduct product = null;
        if(Objects.isNull(request.getId())){
            product = iHomeAutoProductService.add(request);
        }else {
            product = iHomeAutoProductService.update(request);
        }
        return returnSuccess(product);
    }

    @ApiOperation(value = "删除产品", notes = "删除产品")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @DeleteMapping("delete/{id}")
    public Response delete(@PathVariable("id") Long id){
        iHomeAutoProductService.delete(id);
        return returnSuccess();
    }

    @ApiOperation(value = "获取故障类型下拉列表", notes = "获取故障类型下拉列表")
    @GetMapping("get/errorTypes")
    public Response<List<SelectedIntegerVO>> getErrorTypes(){
        List<SelectedIntegerVO> result = iHomeAutoProductService.getErrorTypes();
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增产品故障属性", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("error/add")
    public Response addErrorAttr(@RequestBody @Valid ProductAttributeErrorDTO request){
        iProductAttributeErrorService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改产品故障属性", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("error/update")
    public Response updateErrorAttr(@RequestBody @Valid ProductAttributeErrorDTO request){
        iProductAttributeErrorService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除产品故障属性", notes = "删除产品故障属性")
    @PostMapping("delete/error/{attrId}")
    public Response deleteErrorAttrById(@PathVariable("attrId") String attrId){
        iProductAttributeErrorService.deleteErrorAttrById(attrId);
        return returnSuccess();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("page")
    public Response<BasePageVO<ProductPageVO>> page(ProductQryDTO request){
        BasePageVO<ProductPageVO> result = iHomeAutoProductService.page(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看产品详情", notes = "获取校验模式下拉列表")
    @GetMapping("detail/{id}")
    public Response<ProductDetailVO> getProductDetailInfo(@PathVariable("id") String id){
        ProductDetailVO result = iHomeAutoProductService.getProductDetailInfo(id);
        return returnSuccess(result);
    }


    @ApiOperation(value = "获取产品下拉列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/products")
    public Response<List<SelectedLongVO>> getListProductSelect(){
        List<SelectedLongVO> result = iHomeAutoProductService.getListProductSelect();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取协议下拉列表", notes = "获取协议下拉列表")
    @GetMapping("get/protocols")
    public Response<List<SelectedVO>> getProtocols(){
        List<SelectedVO> result = iHomeAutoProductService.getProtocols();
        return returnSuccess(result);
    }




//    @ApiOperation("三级联动-所有产品类别")
//    @GetMapping("/all-product-cascade")
//    public Response<List<CascadeVo>> allProductType(){
//        List<CascadeVo> result = iHomeAutoProductService.allProductType();
//        return returnSuccess(result);
//    }


//    @ApiOperation(value = " 刷新产品故障属性信息缓存", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("refresh/error-cache")
//    public Response refreshProductErrorCache(){
//        productErrorSchedule.saveData();
//        return returnSuccess();
//    }


//
//    @ApiOperation(value = "获取波特率下拉列表", notes = "获取波特率下拉列表")
//    @GetMapping("get/baudRates")
//    public Response<List<SelectedIntegerVO>> getBaudRates(){
//        List<SelectedIntegerVO> result = iHomeAutoProductService.getBaudRates();
//        return returnSuccess(result);
//    }
//
//
//    @ApiOperation(value = "获取校验模式下拉列表", notes = "获取校验模式下拉列表")
//    @GetMapping("get/checkModes")
//    public Response<List<SelectedIntegerVO>> getCheckModes(){
//        List<SelectedIntegerVO> result = iHomeAutoProductService.getCheckModes();
//        return returnSuccess(result);
//    }
//
//    @ApiOperation(value = "获取性质类型下拉列表", notes = "获取校验模式下拉列表")
//    @GetMapping("get/natures")
//    public Response<List<SelectedIntegerVO>> getNatures(){
//        List<SelectedIntegerVO> result = iHomeAutoProductService.getNatures();
//        return returnSuccess(result);
//    }
//

//
//    @ApiOperation(value = "获取某一产品只读属性下拉列表", notes = "获取某一产品只读属性下拉列表")
//    @GetMapping("get/list/attrs/filter/{productId}")
//    public Response<List<SelectedVO>> getReadAttrSelects(@PathVariable("productId")String productId){
//        List<SelectedVO> result = iHomeAutoProductService.getReadAttrSelects(productId);
//        return returnSuccess(result);
//    }


//    @ApiOperation(value = "查看产品故障属性", notes = "获取校验模式下拉列表")
//    @PostMapping("errors/{productId}")
//    public Response<List<ProductAttributeErrorVO>> getListErrorInfo(@PathVariable("productId") String productId){
//        List<ProductAttributeErrorVO> result =iProductAttributeErrorService.getListAttributesErrorsDeatil(productId);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "删除产品故障属性", notes = "删除产品故障属性")
//    @PostMapping("delete/error/{attrId}")
//    public Response deleteErrorAttrById(@PathVariable("attrId") String attrId){
//        iProductAttributeErrorService.deleteErrorAttrById(attrId);
//        return returnSuccess();
//    }




}
