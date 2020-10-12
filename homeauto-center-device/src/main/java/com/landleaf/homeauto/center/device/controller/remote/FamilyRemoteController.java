//package com.landleaf.homeauto.center.device.controller.remote;
//
//import com.landleaf.homeauto.common.constant.CommonConst;
//import com.landleaf.homeauto.common.domain.Response;
//import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionDTO;
//import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionQryDTO;
//import com.landleaf.homeauto.common.web.BaseController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @ClassName FamilyRemoteController
// * @Description: TODO
// * @Author shizg
// * @Date 2020/10/12
// * @Version V1.0
// **/
//@RestController
//@RequestMapping("/remote/family/")
//@Api(value = "/remote/family/", tags = {"家庭相关内部接口"})
//public class FamilyRemoteController extends BaseController {
//
//    @Autowired
//    private F
//
//    @ApiOperation(value = "判断用户在所绑定的家庭中是否是管理员", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping("check/admin/{userId}")
//    public Response<List<AttributePrecisionDTO>> checkAdmin(@RequestBody AttributePrecisionQryDTO request){
//        List<AttributePrecisionDTO> result = iProductAttributeErrorService.getAttributePrecision(request);
//        return returnSuccess(result);
//    }
//}
