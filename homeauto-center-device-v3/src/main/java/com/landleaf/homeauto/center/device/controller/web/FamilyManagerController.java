package com.landleaf.homeauto.center.device.controller.web;


import cn.hutool.db.Page;
import com.landleaf.homeauto.center.device.model.vo.familymanager.FamilyManagerPageVO;
import com.landleaf.homeauto.center.device.service.mybatis.AddressService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyManagerService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/web/family/manager")
@Api(description = "住户管理")
public class FamilyManagerController extends BaseController {

    @Autowired
    private IFamilyManagerService iFamilyManagerService;



    @ApiOperation(value = "获取地址级联数据（全部小区）", consumes = "application/json")
    @GetMapping(value = "/cascadeList")
    public Response<BasePageVO<FamilyManagerPageVO>> page() {
//        BasePageVO<FamilyManagerPageVO> vos = iFamilyManagerService.page(null);
        return returnSuccess();
    }



}
