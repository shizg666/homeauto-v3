package com.landleaf.homeauto.center.device.controller.remote;

import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyAddDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName ProductRemoteController
 * @Description: 产品相关内部接口
 * @Author shizg
 * @Date 2020/9/4
 * @Version V1.0
 **/
@RestController
@RequestMapping("/remote/product/")
@Api(value = "/remote/product/", tags = {"产品相关内部接口"})
public class ProductRemoteController extends BaseController {
    @Autowired
    private IProductAttributeErrorService iProductAttributeErrorService;

    @ApiOperation(value = "获取故障属性信息（没查到返回null）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("get/error/info")
    public Response<AttributeErrorDTO> getErrorAttributeInfo(@RequestBody AttributeErrorQryDTO request){
        AttributeErrorDTO result = iProductAttributeErrorService.getErrorAttributeInfo(request);
        return returnSuccess(result);
    }
}
