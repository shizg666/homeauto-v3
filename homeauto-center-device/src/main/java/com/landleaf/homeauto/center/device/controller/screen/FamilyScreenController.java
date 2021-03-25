package com.landleaf.homeauto.center.device.controller.screen;

import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yujiumin
 * @version 2020/8/19
 */
@RestController
@RequestMapping("screen/family")
@Api(tags = "大屏家庭接口")
public class FamilyScreenController extends BaseController {

    private IHomeAutoFamilyService familyService;

    @GetMapping("info")
    @ApiOperation("通过终端mac地址获取家庭信息")
    public Response<FamilyInfoBO> getFamilyInfo(@RequestParam String terminalMac) {
        FamilyInfoBO familyInfoBO = familyService.getFamilyInfoByTerminalMac(terminalMac);
        return returnSuccess(familyInfoBO);
    }
//
//    @PostMapping("bind")
//    @ApiOperation("大屏绑定家庭")
//    public Response bind(@RequestBody AdapterHttpFamilyBindDTO adapterHttpFamilyBindDTO) {
//        familyService.bind(adapterHttpFamilyBindDTO.getTerminalMac(),adapterHttpFamilyBindDTO.getFamilyCode());
//        return returnSuccess();
//    }

    @Autowired
    public void setFamilyService(IHomeAutoFamilyService familyService) {
        this.familyService = familyService;
    }

}
