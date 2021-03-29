package com.landleaf.homeauto.contact.screen.controller.outer;


import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.util.StreamUtils;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHttpResponse;
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
     * 大屏主动绑定请求 兼容3.0
     */
    @RequestMapping(value = "/family/bind", method = {RequestMethod.POST})
    public ContactScreenHttpResponse familyBind(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_BIND_REQUEST);
    }

    /**
     * 楼层房间设备配置信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/floor-room-device/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse floorRoomDeviceList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_FLOOR_ROOM_DEVICE_REQUEST);
    }


    /**
     * 场景信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/scene/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse sceneList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_SCENE_REQUEST);

    }


    /**
     * 判断是否是节假日
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/holidays/check", method = {RequestMethod.POST})
    public ContactScreenHttpResponse holidaysCheck(HttpServletRequest request) throws Exception {

        return handleRequest(request, ContactScreenNameEnum.HOLIDAYS_CHECK);
    }

    /**
     * 消息公告信息请求
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/news/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse newsList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_NEWS_REQUEST);

    }

    /**
     * 查询天气
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/weather", method = {RequestMethod.POST})
    public ContactScreenHttpResponse weahter(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_WEATHER_REQUEST);

    }
    /**
     * 定时场景配置信息请求 兼容3.0
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/timing/scene/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse smartSceneTimingList(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.FAMILY_SCENE_TIMING_CONFIG_REQUEST);

    }

    /**
     * 定时场景配置 修改/新增 兼容3.0
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/timing/scene/save-update", method = {RequestMethod.POST})
    public ContactScreenHttpResponse timingSceneSaveOrUpdate(HttpServletRequest request) {

        return handleRequest(request, ContactScreenNameEnum.TIMING_SCENE_SAVE_UPDATE);
    }

    /**
     * 定时场景配置删除 兼容3.0
     */
    @ApiImplicitParam(name = CommonConst.HEADER_MAC, value = "大屏mac", paramType = "header", required = true)
    @RequestMapping(value = "/timing/scene/delete", method = {RequestMethod.POST})
    public ContactScreenHttpResponse timingSceneDelete(HttpServletRequest request) {


        return handleRequest(request, ContactScreenNameEnum.TIMING_SCENE_DELETE);
    }

    /**
     * 请求通用处理方法
     *
     * @param request        请求体
     * @param screenNameEnum 协议名称
     */
    private ContactScreenHttpResponse handleRequest(HttpServletRequest request, ContactScreenNameEnum screenNameEnum) {
        String bodyParams = null;
        try {
            byte[] body = StreamUtils.getByteByStream(request.getInputStream());
            bodyParams = new String(body, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.info("获取body参数异常");
        }
        ContactScreenHttpResponse result = null;
        if (StringUtils.isNotEmpty(bodyParams)) {
            log.info("大屏请求，,请求参数:{}", bodyParams);
        }
        try {
            return (ContactScreenHttpResponse) homeAutoHttpRequestDispatch.dispatch(bodyParams, screenNameEnum);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }


}
