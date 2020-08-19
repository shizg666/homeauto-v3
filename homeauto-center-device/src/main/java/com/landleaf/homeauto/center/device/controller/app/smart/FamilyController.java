package com.landleaf.homeauto.center.device.controller.app.smart;

import com.baomidou.mybatisplus.extension.api.R;
import com.landleaf.homeauto.center.device.model.vo.app.FamilyVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yujiumin
 * @version 2020/8/19
 */
@RestController
@RequestMapping("smart/family")
@Api(tags = "户式化APP家庭接口")
public class FamilyController extends BaseController {

    private IHomeAutoFamilyService familyService;

    @GetMapping
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(String userId) {
        FamilyVO familyVO = familyService.getFamilyListByUserId(userId);
        return returnSuccess(familyVO);
    }

    @Autowired
    public void setFamilyService(IHomeAutoFamilyService familyService) {
        this.familyService = familyService;
    }
}
