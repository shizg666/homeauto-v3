package com.landleaf.homeauto.center.device.controller.app.smart;


import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAppVersionService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * app版本前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/app/smart/app-version")
public class AppVersionController extends BaseController {

    @Autowired
    private IHomeAutoAppVersionService homeAutoAppVersionService;

    @ApiOperation("根据app类型获取当前版本")
    @GetMapping("/current/{appType}")
    public Response<AppVersionDTO> currentVersion(@PathVariable("appType") Integer appType) {
        AppVersionDTO version = homeAutoAppVersionService.getCurrentVersion(appType);
        return returnSuccess(version);
    }

}
