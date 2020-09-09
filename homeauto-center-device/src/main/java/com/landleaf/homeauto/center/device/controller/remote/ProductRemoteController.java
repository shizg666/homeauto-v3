package com.landleaf.homeauto.center.device.controller.remote;

import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionQryDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @ApiOperation(value = " 查询产品属性精度(code 为null，查产品所有属性)", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("get/attr-precision/info")
    public Response<List<AttributePrecisionDTO>> getAttributePrecision(@RequestBody AttributePrecisionQryDTO request){
        List<AttributePrecisionDTO> result = iProductAttributeErrorService.getAttributePrecision(request);
        return returnSuccess(result);
    }
}
