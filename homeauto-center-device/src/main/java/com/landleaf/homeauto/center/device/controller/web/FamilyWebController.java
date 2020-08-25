package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserVO;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 楼栋表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/family")
@Api(value = "/web/family/", tags = {"家庭配置接口"})
public class FamilyWebController extends BaseController {



    @ApiOperation(value = "查询用户家庭列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("list/user")
    public Response<List<FamilyUserVO>> getListByUser(){
        return returnSuccess();
    }

}
