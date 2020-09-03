package com.landleaf.homeauto.center.device.controller.screen;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeWebDTO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpDeleteTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpMqttCallBackDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpTimingSceneResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpWeatherResponseDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wenyilu
 */
@RestController
@RequestMapping("contact-screen")
@Api(tags = "大屏apk接口")
@Slf4j
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

    @ApiOperation("天气请求")
    @PostMapping("/weather")
    Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO) {


        return returnSuccess(contactScreenService.getWeather(adapterMessageHttpDTO.getFamilyId()));
    }

    /**
     * 定时场景获取
     */
    @ApiOperation("获取场景定时配置")
    @PostMapping("/timing/scene/list")
    Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO) {

        return returnSuccess(contactScreenService.getTimingSceneList(adapterMessageHttpDTO.getFamilyId()));
    }

    /**
     * 定时场景 删除
     */
    @ApiOperation("定时场景 删除")
    @PostMapping("/timing/scene/delete")
    Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody AdapterHttpDeleteTimingSceneDTO adapterMessageHttpDTO) {
        return returnSuccess(contactScreenService.deleteTimingScene(adapterMessageHttpDTO.getIds(), adapterMessageHttpDTO.getFamilyId()));
    }


    /**
     * 定时场景 新增/修改
     */
    @ApiOperation("定时场景 新增/修改")
    @PostMapping("/timing/scene/save-update")
    Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos,
                                                                             @RequestParam("familyId") String familyId) {

        return returnSuccess(contactScreenService.saveOrUpdateTimingScene(dtos, familyId));

    }

    /**
     * 更新终端状态
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @ApiOperation("更新终端状态")
    @PostMapping("/update/terminal/status")
    Response updateTerminalOnLineStatus(@RequestBody AdapterHttpMqttCallBackDTO adapterMessageHttpDTO) {
       log.info("收到更新大屏状态通知:{}", JSON.toJSONString(adapterMessageHttpDTO));
        contactScreenService.updateTerminalOnLineStatus(adapterMessageHttpDTO.getFamilyId(),
                adapterMessageHttpDTO.getTerminalMac(), adapterMessageHttpDTO.getStatus());
        return returnSuccess();
    }
}
