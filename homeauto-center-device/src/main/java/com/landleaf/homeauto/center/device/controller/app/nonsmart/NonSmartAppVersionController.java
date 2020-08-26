package com.landleaf.homeauto.center.device.controller.app.nonsmart;


import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAppVersionService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 非智能家居（自由方舟）app版本前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/app/non-smart/app-version")
@Api(value = "自由方舟app版本请求", tags = "自由方舟app版本请求相关接口")
public class NonSmartAppVersionController extends BaseController {

    @Autowired
    private IHomeAutoAppVersionService homeAutoAppVersionService;

    @ApiOperation("根据app类型获取当前已推送的最新版本")
    @GetMapping("/current/{appType}")
    public Response<AppVersionDTO> currentVersion(@PathVariable("appType") Integer appType) {
        AppVersionDTO version = homeAutoAppVersionService.getCurrentVersion(appType, AppTypeEnum.NO_SMART.getCode());
        return returnSuccess(version);
    }

}
