package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.AddressService;
import com.landleaf.homeauto.common.domain.Response;
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
@RequestMapping("/web/adress")
@Api(description = "地址接口")
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;



    @ApiOperation(value = "获取地址级联数据（全部小区）", consumes = "application/json")
    @GetMapping(value = "/cascadeList")
    public Response<List<CascadeVo>> cascadeList() {
        List<CascadeVo> vos = addressService.cascadeList(null);
        return returnSuccess(vos);
    }



}
