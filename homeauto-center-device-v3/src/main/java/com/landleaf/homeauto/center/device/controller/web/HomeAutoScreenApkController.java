package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 大屏apk 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/web/screen-apk")
@Api(value = "/web/screen-apk", tags = {"大屏APK管理"})
public class HomeAutoScreenApkController extends BaseController {

    @Autowired
    private IHomeAutoScreenApkService homeAutoScreenApkService;

    @ApiOperation(value = "查看")
    @GetMapping("/info")
    public Response<ScreenApkResDTO> getInfoById (@RequestParam ("id") String id) {
        ScreenApkResDTO data=homeAutoScreenApkService.getInfoById(id);
        return returnSuccess(data);
    }

    @ApiOperation(value = "新增应用")
    @PostMapping("save")
    public Response saveApk(@RequestBody ScreenApkSaveDTO requestBody) {
        homeAutoScreenApkService.saveApk(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "修改应用")
    @PostMapping("update")
    public Response updateApk(@RequestBody ScreenApkDTO requestBody) {
        homeAutoScreenApkService.updateApk(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "应用查询条件动态获取")
    @GetMapping(value = "/condition")
    public Response<ScreenApkConditionDTO> getCondition() {
        return returnSuccess(homeAutoScreenApkService.getCondition());
    }

    @ApiOperation(value = "应用列表")
    @PostMapping(value = "/page")
    public Response<BasePageVO<ScreenApkResDTO>> pageListApks(@RequestBody ScreenApkPageDTO requestBody) {
        return returnSuccess(homeAutoScreenApkService.pageListApks(requestBody));
    }

    @ApiOperation(value = "删除应用")
    @PostMapping(value = "/delete")
    public Response deleteApkByIds(@RequestBody List<String> ids) {
        homeAutoScreenApkService.deleteApkByIds(ids);
        return returnSuccess();
    }

}
