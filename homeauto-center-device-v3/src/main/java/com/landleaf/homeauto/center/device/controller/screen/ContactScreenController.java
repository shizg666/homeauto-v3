package com.landleaf.homeauto.center.device.controller.screen;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpDeleteTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpHolidaysCheckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务于大屏的http请求，来源于adapter调用
 *
 * @author wenyilu
 */
@RestController
@RequestMapping("contact-screen")
@Api(tags = "大屏接口")
@Slf4j
public class ContactScreenController extends BaseController {

    @Autowired
    private IContactScreenService contactScreenService;


    @GetMapping("/family/info")
    @ApiOperation("通过终端mac地址获取家庭信息")
    public Response<ScreenFamilyBO> getFamilyInfo(@RequestParam String terminalMac) {
        ScreenFamilyBO familyInfoBO = contactScreenService.getFamilyInfoByTerminalMac(terminalMac);
        return returnSuccess(familyInfoBO);
    }

    @ApiOperation("楼层房间设备信息")
    @PostMapping("/floor-room-device/list")
    Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO) {
        return returnSuccess(contactScreenService.getFloorRoomDeviceList(adapterMessageHttpDTO.getHouseTemplateId()));
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
     * 获取消息公告信息
     */
    @ApiOperation("获取消息公告")
    @PostMapping("/news/list")
    Response<List<ScreenHttpNewsResponseDTO>> getNews(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO) {

        return returnSuccess(contactScreenService.getNews(adapterMessageHttpDTO.getFamilyId()));
    }

    /**
     * 获取场景
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @ApiOperation("获取场景")
    @PostMapping("/scene/list")
    Response<List<SyncSceneInfoDTO>> getSceneList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO) {
        return returnSuccess(contactScreenService.getSceneList(adapterMessageHttpDTO.getFamilyId()));
    }

    /**
     * 节假日判定
     */
    @PostMapping("/holidays/check")
    Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody AdapterHttpHolidaysCheckDTO holidaysCheckDTO) {
        return returnSuccess(contactScreenService.holidayCheck(holidaysCheckDTO.getDate()));
    }
}
