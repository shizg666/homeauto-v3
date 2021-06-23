package com.landleaf.homeauto.center.device.controller.applets;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.BindFamilyDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZFamilyUserDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JzappletesUserDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName JHAppletsController
 * @Description: 常州嘉宏小程序接口
 * @Author shizg
 * @Date 2021/6/22
 * @Version V1.0
 **/
@RestController
@RequestMapping("/jh/applets")
@Api(description = "常州嘉宏小程序接口")
public class JZAppletsController extends BaseController {

    @Autowired
    private IJHAppletsrService ijhAppletsrService;

    @ApiOperation(value = "新增用户", notes = "")
    @PostMapping("add-user")
    public Response<JzappletesUserDTO> addUser(@RequestBody @Valid JzappletesUserDTO request){
        JzappletesUserDTO user = ijhAppletsrService.addUser(request);
        return returnSuccess(user);
    }

    @ApiOperation(value = "绑定家庭", notes = "")
    @PostMapping("bind-family")
    public Response<Long> bindFamily(@RequestBody @Valid BindFamilyDTO request){
        Long familyId = ijhAppletsrService.bindFamily(request);
        return returnSuccess(familyId);
    }

    @ApiOperation(value = "添加成员", notes = "")
    @PostMapping("add/family-user")
    public Response addFamilyUser(@RequestBody @Valid JZFamilyUserDTO request){
//        ijhAppletsrService.addFamilyUsers(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除成员", notes = "")
    @PostMapping("remove-family-user/{id}")
    public Response removeFamilyUserById(@PathVariable  Long id){
//        ijhAppletsrService.removeFamilyUserById(id);
        return returnSuccess();
    }

//    @ApiOperation(value = "管理员转让", notes = "查询", consumes = "application/json")
//    @PostMapping(value = "/admin-transfer")
//    public Response<List<AppFmailyDTO>> adminTransfer(@RequestBody AppProjectRoleDTO requestVO) {
//        iSmarthomeProjectUserService.adminTransfer(requestVO);
//        return returnSuccess();
//    }

}
