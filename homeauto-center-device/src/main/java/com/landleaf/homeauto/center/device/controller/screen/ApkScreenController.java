package com.landleaf.homeauto.center.device.controller.screen;

import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenyilu
 */
@RestController
@RequestMapping("screen/apk-version")
@Api(tags = "大屏apk接口")
public class ApkScreenController extends BaseController {


    @PostMapping("check")
    @ApiOperation("大屏apk版本检测")
    Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {


        return returnSuccess();

    }

}
