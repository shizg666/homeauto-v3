package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ScreenApkUpdateSaveDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkUpdateService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 大屏apk更新记录 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/web/screen-apk-update")
@Api(value = "/web/screen-apk-update", tags = {"大屏APK推送管理"})
public class HomeAutoScreenApkUpdateController extends BaseController {


    @Autowired
    private IHomeAutoScreenApkUpdateService homeAutoScreenApkUpdateService;

    @ApiOperation(value = "新增推送记录")
    @PostMapping("/save")
    public Response saveApkUpdate(@RequestBody ScreenApkUpdateSaveDTO requestBody) {
        HomeAutoScreenApkUpdateDO apkUpdate = homeAutoScreenApkUpdateService.saveApkUpdate(requestBody);
        return returnSuccess(apkUpdate);
    }

    @ApiOperation(value = "重新推送")
    @GetMapping("/retry/save")
    public Response retrySave(@RequestParam String detailId) {
        homeAutoScreenApkUpdateService.retrySave(detailId);
        return returnSuccess();
    }


}
