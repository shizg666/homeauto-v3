package com.landleaf.homeauto.center.device.controller.screen;

import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Response<FamilyInfoBO> getFamilyInfo(@RequestParam Integer terminalType, @RequestParam String terminalMac) {
        FamilyInfoBO familyInfoBO = familyService.getFamilyInfoByTerminalMac(terminalMac, terminalType);
        return returnSuccess(familyInfoBO);
    }

    @Autowired
    public void setFamilyService(IHomeAutoFamilyService familyService) {
        this.familyService = familyService;
    }

}
