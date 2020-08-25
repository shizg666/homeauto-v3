package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkPushingDetailReqDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkPushingResDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkUpdateDetailPageDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkUpdateDetailResDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoScreenApkUpdateDetailService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 大屏apk更新记录详情 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/web/screen-apk-update/detail")
@Api(value = "/web/screen-apk-update/detail", tags = {"大屏APK推送详情管理"})
public class HomeAutoScreenApkUpdateDetailController extends BaseController {

    @Autowired
    private IHomeAutoScreenApkUpdateDetailService homeAutoScreenApkUpdateDetailService;

    @ApiOperation(value = "正在推送")
    @PostMapping(value = "/pushing/list")
    public Response<ApkPushingResDTO> pushingDetails(@RequestBody ApkPushingDetailReqDTO requestBody) {
        return returnSuccess(homeAutoScreenApkUpdateDetailService.pushingDetails(requestBody.getApkId()));
    }
    @ApiOperation(value = "历史推送")
    @PostMapping(value = "/page")
    public Response<BasePageVO<ApkUpdateDetailResDTO>> pageListApkUpdateDetail(@RequestBody ApkUpdateDetailPageDTO requestBody) {
        return returnSuccess(homeAutoScreenApkUpdateDetailService.pageListApkUpdateDetail(requestBody));
    }

}
