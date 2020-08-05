//package com.landleaf.homeauto.center.device.controller.web;
//
//
//import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
//import com.landleaf.homeauto.common.constance.CommonConst;
//import com.landleaf.homeauto.common.domain.Response;
//import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;
//import com.landleaf.homeauto.common.domain.vo.category.CategoryDTO;
//import com.landleaf.homeauto.common.web.BaseController;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * <p>
// * 品类表 前端控制器
// * </p>
// *
// * @author wenyilu
// * @since 2020-08-03
// */
//@RestController
//@RequestMapping("/category/")
//public class HomeAutoCategoryController extends BaseController {
//
//    @Autowired
//    private IHomeAutoCategoryService iHomeAutoCategoryService;
//
//    @ApiOperation(value = "新增类别", notes = "新增类别")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("add")
//    public Response add(@RequestBody CategoryDTO request){
//        iHomeAutoCategoryService.add(request);
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "修改类别", notes = "修改类别")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update")
//    public Response update(@RequestBody AttribureDicDTO request){
//        iHomeAutoCategoryService.update(request);
//        return returnSuccess();
//    }
//
//}
