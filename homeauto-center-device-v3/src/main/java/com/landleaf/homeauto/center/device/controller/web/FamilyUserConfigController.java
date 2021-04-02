//package com.landleaf.homeauto.center.device.controller.web;
//
//
//import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
//import com.landleaf.homeauto.center.device.model.vo.family.*;
//import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateTerminalVO;
//import com.landleaf.homeauto.center.device.service.mybatis.*;
//import com.landleaf.homeauto.common.constant.CommonConst;
//import com.landleaf.homeauto.common.domain.Response;
//import com.landleaf.homeauto.common.domain.vo.SelectedVO;
//import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
//import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
//import com.landleaf.homeauto.common.domain.vo.realestate.ProjectVO;
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
// * 楼栋表 前端控制器
// * </p>
// *
// * @author wenyilu
// * @since 2020-08-11
// */
//@RestController
//@RequestMapping("/web/family-user/config/")
//@Api(value = "/web/family-user/config/", tags = {"家庭组配置接口"})
//public class FamilyUserConfigController extends BaseController {
//
//    @Autowired
//    private IFamilyUserService iFamilyUserService;
//
//
//    @ApiOperation(value = "获取家庭成员类型列表", notes = "查询", consumes = "application/json")
//    @GetMapping(value = "types")
//    public Response<List<SelectedVO>> getMenberTypes() {
//        List<SelectedVO> result =  iFamilyUserService.getMenberTypes();
//        return returnSuccess(result);
//    }
//
//
//    @ApiOperation(value = "根据用户ID获取工程列表", notes = "查询", consumes = "application/json")
//    @GetMapping(value = "/get-list-project-By-userId/{userId}")
//    public Response<List<ProjectVO>> getListProjectByUserId(@PathVariable("userId") String userId) {
//        List<ProjectVO> result =  iFamilyUserService.getListProjectByUserId(userId);
//        return returnSuccess(result);
//    }
//
//
//    @ApiOperation(value = "获取家庭组信息列表", notes = "查询", consumes = "application/json")
//    @GetMapping(value = "/getFamilyMemberList/{projectId}")
//    public Response<List<ProjectUserResponseVO>> getFamilyMemberList(@PathVariable String projectId) {
//        List<ProjectUserResponseVO> result =  iSmarthomeProjectUserService.getFamilyMemberList(projectId);
//        return returnSuccess(result);
//    }
//
//
//    @ApiOperation(value = "添加家庭成员", notes = "添加", consumes = "application/json")
//    @PostMapping(value = "/add")
//    public Response<String> add(@RequestBody @Valid ProjectUserAddVO requestObject) {
//        iSmarthomeProjectUserService.addMember(requestObject);
//        asynBusinessHandle.sendAddFmailySmsMsg(requestObject);
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "app添加家庭成员", notes = "添加", consumes = "application/json")
//    @PostMapping(value = "/addOnApp")
//    public Response<String> addOnApp(@RequestBody @Valid ProjectUserAddVO requestObject) {
//        iSmarthomeProjectUserService.addOnApp(requestObject);
//
//        asynBusinessHandle.sendAddFmailySmsMsg(requestObject);
//        return returnSuccess();
//    }
//
//
//    @ApiOperation(value = "删除成员", notes = "删除", consumes = "application/json")
//    @PostMapping("/delete/{id}")
//    public Response delete(@PathVariable("id") String id ) {
//        iSmarthomeProjectUserService.deleteById(id);
//        return returnSuccess();
//    }
//
//    @ApiOperation(value = "设置为管理员", notes = "", consumes = "application/json")
//    @PostMapping("/settingAdmin")
//    public Response settingAdmin(@RequestBody SettingProjectUserVO requestObject) {
//        iSmarthomeProjectUserService.settingAdmin(requestObject);
//        return returnSuccess();
//    }
//
//
//    @ApiOperation(value = "用户注销解绑判断", notes = "", consumes = "application/json")
//    @PostMapping("/logOut/{userId}")
//    public Response<CheckResultVO> logOutUserCHeck( @PathVariable("userId") String userId) {
//        CheckResultVO checkResultVO = iSmarthomeProjectUserService.logOutUserCHeck(userId);
//        return returnSuccess(checkResultVO);
//    }
//
//    @ApiOperation(value = "根据用户手机号获取绑定工程信息", notes = "", consumes = "application/json")
//    @GetMapping("/getListProjects/{phone}")
//    public Response<List<ProjectVO>> getListProjectByUserPhone( @PathVariable("phone") String phone) {
//        List<ProjectVO> data = iSmarthomeProjectUserService.getListProjectByUserPhone(phone);
//        return returnSuccess(data);
//    }
//
//
//
////    @ApiOperation(value = "根据用户id获取工程信息", notes = "查询", consumes = "application/json")
////    @GetMapping(value = "/getUserProjectList")
////    public Response<List<ProjectInfoResponseDTO>> getUserProjectList(String userId) {
////        List<ProjectInfoResponseDTO> result =  iSmarthomeProjectUserService.getUserProjectList(userId);
////        return returnSuccess(result);
////    }
//
//    //*************app******************************
//    @ApiOperation(value = "获取工程家庭组列表", notes = "查询", consumes = "application/json")
//    @GetMapping(value = "/getFamilyList")
//    public Response<List<AppFmailyDTO>> getFamilyList(){
//        String userId = TokenContext.getToken().getUserId();
//        List<AppFmailyDTO> result = iSmarthomeProjectUserService.getAppFamilyMemberList(userId);
//        return returnSuccess(result);
//    }
//
//    @ApiOperation(value = "app管理员转让", notes = "查询", consumes = "application/json")
//    @PostMapping(value = "/admin-transfer")
//    public Response<List<AppFmailyDTO>> adminTransfer(@RequestBody AppProjectRoleDTO requestVO) {
//        iSmarthomeProjectUserService.adminTransfer(requestVO);
//        return returnSuccess();
//    }
//
//
//
//
//
//
//}
