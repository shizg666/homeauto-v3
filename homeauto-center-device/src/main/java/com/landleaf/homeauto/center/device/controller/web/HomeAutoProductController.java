//package com.landleaf.homeauto.center.device.controller.web;
//
//
//import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeInfoService;
//import com.landleaf.homeauto.common.constance.CommonConst;
//import com.landleaf.homeauto.common.domain.Response;
//import com.landleaf.homeauto.common.domain.vo.BasePageVO;
//import com.landleaf.homeauto.common.domain.vo.category.CategoryDTO;
//import com.landleaf.homeauto.common.domain.vo.category.CategoryPageVO;
//import com.landleaf.homeauto.common.domain.vo.category.CategoryQryDTO;
//import com.landleaf.homeauto.common.web.BaseController;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
///**
// * <p>
// * 产品表 前端控制器
// * </p>
// *
// * @author wenyilu
// * @since 2020-08-03
// */
//@RestController
//@RequestMapping("/product/")
//public class HomeAutoProductController extends BaseController {
//
//    @Autowired
//    private IHomeAutoCategoryAttributeInfoService iHomeAutoCategoryAttributeInfoService;
//
//    @ApiOperation(value = "新增产品", notes = "新增类别")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("add")
//    public Response add(@RequestBody @Valid CategoryDTO request){
////        iHomeAutoCategoryAttributeInfoService.add();
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "修改类别", notes = "修改类别")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update")
//    public Response update(@RequestBody CategoryDTO request){
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "删除类别", notes = "删除类别")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("delete/{id}")
//    public Response update(@PathVariable("id") String categoryId){
//        return returnSuccess();
//    }
//
//
//    @ApiOperation(value = "分页查询", notes = "分页查询")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("page")
//    public Response<BasePageVO<CategoryPageVO>> page(@RequestBody CategoryQryDTO request){
//        return returnSuccess();
//    }
//
//
//}
