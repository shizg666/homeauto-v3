package com.landleaf.homeauto.center.device.controller.screen;

import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenyilu
 */
@RestController
@RequestMapping("contact-screen")
@Api(tags = "大屏apk接口")
public class ContactScreenController extends BaseController {

    @Autowired
    private IContactScreenService contactScreenService;

    @PostMapping("/apk-version/check")
    @ApiOperation("大屏apk版本检测")
    Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {

        return returnSuccess(contactScreenService.apkVersionCheck(adapterHttpApkVersionCheckDTO));

    }

    @ApiOperation("楼层房间设备信息")
    @PostMapping("/floor-room-device/list")
    Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO) {

        return returnSuccess(contactScreenService.getFloorRoomDeviceList(adapterMessageHttpDTO.getFamilyId()));

    }

}
