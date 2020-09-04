package com.landleaf.homeauto.center.device.controller.feign;

import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/4
 */
@RestController
@RequestMapping("/feign/family")
public class FamilyFeignController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;

    @GetMapping("/exist/{familyId}")
    public Response<Boolean> familyExist(@PathVariable String familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        return returnSuccess(!Objects.isNull(familyDO));
    }

}
