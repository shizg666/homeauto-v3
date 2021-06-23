package com.landleaf.homeauto.center.device.controller.applets;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.BindFamilyDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JhappletesUserDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class JHAppletsController extends BaseController {

    @Autowired
    private IJHAppletsrService ijhAppletsrService;

    @ApiOperation(value = "新增用户", notes = "")
    @PostMapping("addUser")
    public Response<JhappletesUserDTO> addUser(@RequestBody @Valid JhappletesUserDTO request){
        JhappletesUserDTO user = ijhAppletsrService.addUser(request);
        return returnSuccess(user);
    }

    @ApiOperation(value = "绑定家庭", notes = "")
    @PostMapping("bindFamily")
    public Response bindFamily(@RequestBody @Valid BindFamilyDTO request){
        JhappletesUserDTO user = ijhAppletsrService.bindFamily(request);
        return returnSuccess(user);
    }
}
