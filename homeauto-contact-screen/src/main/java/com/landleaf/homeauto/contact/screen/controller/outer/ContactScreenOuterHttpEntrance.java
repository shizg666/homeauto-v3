package com.landleaf.homeauto.contact.screen.controller.outer;


import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.util.StreamUtils;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttResponse;
import com.landleaf.homeauto.contact.screen.handle.HomeAutoRequestDispatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 大屏http/https请求入口
 *
 * @author wenyilu
 */
@Slf4j
@RestController
@RequestMapping("/contact-screen/screen")
@Api(value = "/contact-screen/screen", tags = "大屏通讯入口")
public class ContactScreenOuterHttpEntrance {

    @Autowired
    private HomeAutoRequestDispatch homeAutoHttpRequestDispatch;


    /**
     * 楼层房间设备配置信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/floor-room-device/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse floorRoomDeviceList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_FLOOR_ROOM_DEVICE_REQUEST);
    }

    /**
     * 场景（自由方舟）信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/non-smart/scene/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse nonSmartSceneList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_SCENE_NON_SMART_REQUEST);
    }

    /**
     * 场景(户式化)信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/scene/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse smartSceneList(HttpServletRequest request) {


        return handleRequest(request, ContactScreenNameEnum.FAMILY_SCENE_REQUEST);

    }

    /**
     * 定时场景信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/scene/timing/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse smartSceneTimingList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_SCENE_TIMING_CONFIG_REQUEST);

    }

    /**
     * 产品信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/product/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse productList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_PRODUCT_REQUEST);

    }

    /**
     * 消息公告信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/news/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse newsList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_NEWS_REQUEST);

    }

    /**
     * 预约（自由方舟）信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/non-smart/reservation/list", method = {RequestMethod.POST})
    public ContactScreenMqttResponse nonSmartReservationList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_RESERVATION_NO_SMART_REQUEST);

    }

    /**
     * 查询天气
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/weahter", method = {RequestMethod.POST})
    public ContactScreenMqttResponse weahter(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_WEATHER_REQUEST);

    }

    /**
     * 获取家庭码
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/familyCode", method = {RequestMethod.POST})
    public ContactScreenMqttResponse familyCode(HttpServletRequest request) {


        return handleRequest(request, ContactScreenNameEnum.FAMILY_FAMILY_CODE_REQUEST);
    }

    /**
     * 预约（自由方舟）修改/新增
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/non-smart/reservation/save-update", method = {RequestMethod.POST})
    public ContactScreenMqttResponse nonSmartReservationSaveOrUpdate(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.NON_SMART_RESERVATION_SAVE_UPDATE);
    }

    /**
     * 预约（自由方舟）删除
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/non-smart/reservation/delete", method = {RequestMethod.POST})
    public ContactScreenMqttResponse nonSmartReservationDelete(HttpServletRequest request) {


        return handleRequest(request, ContactScreenNameEnum.NON_SMART_RESERVATION_DELETE);
    }

    /**
     * 场景（自由方舟）修改/新增
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/non-smart/scene/save-update", method = {RequestMethod.POST})
    public ContactScreenMqttResponse nonSmartSceneSaveOrUpdate(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.NON_SMART_SCENE_SAVE_UPDATE);
    }

    /**
     * 场景（自由方舟）删除
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/non-smart/scene/delete", method = {RequestMethod.POST})
    public ContactScreenMqttResponse nonSmartSceneDelete(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.NON_SMART_SCENE_DELETE);
    }

    /**
     * 判断是否是节假日
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/holidays/check", method = {RequestMethod.POST})
    public ContactScreenMqttResponse holidaysCheck(HttpServletRequest request) throws Exception {

        return handleRequest(request, ContactScreenNameEnum.HOLIDAYS_CHECK);
    }


    /**
     * 请求通用处理方法
     *
     * @param request        请求体
     * @param screenNameEnum 协议名称
     */
    private ContactScreenMqttResponse handleRequest(HttpServletRequest request, ContactScreenNameEnum screenNameEnum) {
        String bodyParams = null;
        try {
            byte[] body = StreamUtils.getByteByStream(request.getInputStream());
            bodyParams = new String(body, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.info("获取body参数异常");
        }
        ContactScreenMqttResponse result = null;
        if (StringUtils.isNotEmpty(bodyParams)) {
            log.info("大屏请求，,请求参数:{}", bodyParams);
        }
        try {
            return homeAutoHttpRequestDispatch.dispatch(bodyParams, screenNameEnum);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }


}
