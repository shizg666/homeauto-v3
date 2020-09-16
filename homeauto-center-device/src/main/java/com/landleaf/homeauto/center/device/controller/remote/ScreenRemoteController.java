package com.landleaf.homeauto.center.device.controller.remote;

import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.center.device.service.mybatis.IVacationSettingService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ProductRemoteController
 * @Description: 产品相关内部接口
 * @Author shizg
 * @Date 2020/9/4
 * @Version V1.0
 **/
@RestController
@RequestMapping("/remote/screen/")
@Api(value = "/remote/screen/", tags = {"大屏相关接口"})
public class ScreenRemoteController extends BaseController {
    @Autowired
    private IVacationSettingService iVacationSettingService;

    @ApiOperation(value = "获取当天的类别 0工作日 1节假日 ", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/today/type/{day}")
    public Response<Integer> getSomeDayType(@PathVariable("day") String day){
        Integer type = iVacationSettingService.getSomeDayType(day);
        return returnSuccess(type);
    }

}
