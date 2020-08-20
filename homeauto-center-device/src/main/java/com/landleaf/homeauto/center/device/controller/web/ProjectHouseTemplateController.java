//package com.landleaf.homeauto.center.device.controller.web;
//
//
//import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
//import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplatePageVO;
//import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
//import com.landleaf.homeauto.common.constant.CommonConst;
//import com.landleaf.homeauto.common.domain.Response;
//import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
//import com.landleaf.homeauto.common.domain.vo.realestate.ProjectHouseTemplateDTO;
//import com.landleaf.homeauto.common.web.BaseController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * <p>
// * 项目户型表 前端控制器
// * </p>
// *
// * @author wenyilu
// * @since 2020-08-11
// */
//@RestController
//@RequestMapping("/web/project-house-template")
//@Api(value = "/web/project-house-template/", tags = {"项目户型配置接口"})
//public class ProjectHouseTemplateController extends BaseController {
//    @Autowired
//    private IProjectHouseTemplateService iProjectHouseTemplateService;
//
//
//    @ApiOperation(value = "新增户型", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("add")
//    @LogAnnotation(name ="新增户型")
//    public Response add(@RequestBody @Valid ProjectHouseTemplateDTO request){
//        iProjectHouseTemplateService.add(request);
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "修改户型（修改id必传）", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("update")
//    @LogAnnotation(name ="修改户型")
//    public Response update(@RequestBody @Valid ProjectHouseTemplateDTO request){
//        iProjectHouseTemplateService.update(request);
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "删除户型", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("delete")
//    @LogAnnotation(name ="删除户型")
//    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
//        iProjectHouseTemplateService.delete(request);
//        return returnSuccess();
//    }
//
//
//    @ApiOperation(value = "根据项目id获取户型列表", notes = "根据项目id获取户型列表")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping("list/{id}")
//    public Response<List<HouseTemplatePageVO>> getListByProjectId(@PathVariable("id") String id){
//        List<HouseTemplatePageVO> result = iProjectHouseTemplateService.getListByProjectId(id);
//        return returnSuccess(result);
//    }
//
//
////    @ApiOperation(value = "新增大屏/网关", notes = "")
////    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
////    @PostMapping("add")
////    public Response add(@RequestBody @Valid HouseTemplateTerminalVO request){
////        iProjectHouseTemplateService.add(request);
////        return returnSuc  cess();
////    }
//
////    @ApiOperation(value = "修改户型（修改id必传）", notes = "")
////    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
////    @PostMapping("update")
////    @LogAnnotation(name ="修改户型")
////    public Response update(@RequestBody @Valid ProjectHouseTemplateDTO request){
////        iProjectHouseTemplateService.update(request);
////        return returnSuccess();
////    }
//
//}
