package com.landleaf.homeauto.contact.screen.common.enums;

import com.landleaf.homeauto.common.domain.dto.screen.payload.control.*;
import com.landleaf.homeauto.common.domain.dto.screen.payload.event.ContactScreenFamilyEventAlarmPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.event.ContactScreenFamilyEventDeviceOnlinePayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.event.ContactScreenFamilyEventLogsPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.event.ContactScreenFamilyEventReplyPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.notice.ContactScreenDeviceStatusUpdatePayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.notice.ContactScreenDeviceStatusUpdateReplyPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.notice.ContactScreenFamilyConfigUpdatePayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.notice.ContactScreenFamilyConfigUpdateReplyPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.request.config.*;
import com.landleaf.homeauto.common.domain.dto.screen.payload.request.other.ContactScreenFamilyOtherRequestPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.request.other.ContactScreenTimeRequestReplyPayload;
import com.landleaf.homeauto.common.domain.dto.screen.payload.request.other.ContactScreenWeatherRequestReplyPayload;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 与大屏通讯指令名称
 * @author wenyilu
 */
public enum ContactScreenNameEnum {

    DEVICE_WRITE("DeviceWrite", "设备写入","","",1, ContactScreenDeviceWritePayload.class),
    DEVICE_WRITE_REPLY("DeviceWriteReply", "设备写入响应","","",1, ContactScreenDeviceWriteReplyPayload.class),

    DEVICE_STATUS_READ("DeviceStatusRead", "读取状态","","",1, ContactScreenDeviceStatusReadPayload.class),
    DEVICE_STATUS_READ_REPLY("DeviceStatusReadReply", "读取状态响应","","",1, ContactScreenDeviceStatusReadReplyPayload.class),

    FAMILY_SCENE_SET("FamilySceneSet", "控制场景","","",1, ContactScreenFamilySceneSetPayload.class),
    FAMILY_SCENE_SET_REPLY("FamilySceneSetReply", "控制场景响应","","",1, ContactScreenFamilySceneSetReplyPayload.class),

    DEVICE_STATUS_UPDATE("DeviceStatusUpdate", "设备状态更新","","",1, ContactScreenDeviceStatusUpdatePayload.class),
    DEVICE_STATUS_UPDATE_REPLY("DeviceStatusUpdateReply", "设备状态更新响应","","",1, ContactScreenDeviceStatusUpdateReplyPayload.class),

    FAMILY_CONFIG_UPDATE("FamilyConfigUpdate", "配置数据更新通知","","",1, ContactScreenFamilyConfigUpdatePayload.class),
    FAMILY_CONFIG_UPDATE_REPLY("DeviceStatusUpdateReply", "配置数据更新通知响应","","",1, ContactScreenFamilyConfigUpdateReplyPayload.class),

    FAMILY_FLOOR_REQUEST("FamilyFloorRequest", "楼层信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_FLOOR_REQUES_REPLYT("FamilyFloorRequestReply", "楼层信息请求响应","","",1, ContactScreenFamilyFloorRequestReplyPayload.class),

    FAMILY_ROOM_REQUEST("FamilyFloorRequest", "房间信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_ROOM_REQUEST_REPLYT("FamilyFloorRequestReply", "房间信息请求响应","","",1, ContactScreenFamilyRoomRequestReplyPayload.class),

    DEVICE_INFO_REQUEST("DeviceInfoRequest", "设备信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    DEVICE_INFO_REQUEST_REPLY("DeviceInfoRequestReply", "设备信息请求响应","","",1, ContactScreenDeviceInfoRequestReplyPayload.class),

    FAMILY_SCENE_REQUEST("FamilySceneRequest", "场景信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_SCENE_REQUEST_REPLY("FamilySceneRequestReply", "场景信息请求响应","","",1, ContactScreenFamilySceneRequestReplyPayload.class),

    FAMILY_NEWS_REQUEST("FamilyNewsRequest", "消息公告信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_NEWS_REQUEST_REPLY("FamilyNewsRequestReply", "消息公告信息请求响应","","",1, ContactScreenDeviceWritePayload.class),

    FAMILY_PRODUCT_REQUEST("FamilyProductRequest", "产品信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_PRODUCT_REQUEST_REPLY("FamilyProductRequestReply", "产品信息请求响应","","",1, ContactScreenProductRequestReplyPayload.class),

    FAMILY_SCENE_TIMING_CONFIG_REQUEST("FamilySceneTimingConfigRequest", "定时场景信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_SCENE_TIMING_CONFIG_REQUEST_REPLY("FamilySceneTimingConfigRequestReply", "定时场景信息请求响应","","",1, ContactScreenFamilySceneTimeRequestReplyPayload.class),

    FAMILY_SCENE_SMART_CONFIG_REQUEST("FamilySceneSmartConfigRequest", "智能场景信息请求","","",1, ContactScreenFamilyConfigRequestPayload.class),
    FAMILY_SCENE_SMART_CONFIG_REQUEST_REPLY("FamilySceneSmartConfigRequestReply", "智能场景信息请求响应","","",1, ContactScreenFamilySceneSmartRequestReplyPayload.class),

    FAMILY_SCREEN_LOGS_EVENT("FamilyScreenLogsEvent", "大屏日志推送","","",1, ContactScreenFamilyEventLogsPayload.class),
    FAMILY_SCREEN_LOGS_EVENT_REPLY("FamilyScreenLogsEventReply", "大屏日志推送响应","","",1, ContactScreenFamilyEventReplyPayload.class),

    FAMILY_DEVICE_ALARM_EVENT("FamilyDeviceAlarmEvent", "报警信息上报","","",1, ContactScreenFamilyEventAlarmPayload.class),
    FAMILY_DEVICE_ALARM_EVENT_REPLY("FamilyDeviceAlarmEventReply", "报警信息上报响应","","",1, ContactScreenFamilyEventReplyPayload.class),

    DEVICE_ONLINE_STATUS_EVENT("DeviceOnlineStatusEvent", "设备上下线状态推送","","",1, ContactScreenFamilyEventDeviceOnlinePayload.class),
    DEVICE_ONLINE_STATUS_EVENT_REPLY("DeviceOnlineStatusEventReply", "设备上下线状态推送响应","","",1, ContactScreenFamilyEventReplyPayload.class),

    FAMILY_WEATHER_REQUEST("FamilyWeatherRequest", "查询天气","","",1, ContactScreenFamilyOtherRequestPayload.class),
    FAMILY_WEATHER_REQUEST_REPLY("FamilyWeatherRequestReply", "查询天气响应","","",1, ContactScreenWeatherRequestReplyPayload.class),

    FAMILY_TIME_REQUEST("FamilyTimeRequest", "查询时间","","",1, ContactScreenFamilyOtherRequestPayload.class),
    FAMILY_TIME_REQUEST_REPLY("FamilyTimeRequestReply", "查询时间响应","","",1, ContactScreenTimeRequestReplyPayload.class),
    ;
    /**
     * 设备操作类型code
     */
    private String code;
    /**
     * 业务名称
     */
    public String name;
    /**
     * 业务类
     */
    public String beanName;
    /**
     * 方法名
     */
    public String methodName;
    /**
     * 参数类型（1：object,2:list）
     */
    private int paramType;
    /**
     * 参数名
     */
    public Class paramName;

    ContactScreenNameEnum(String code, String name, String beanName, String methodName, int paramType, Class paramName) {
        this.code = code;
        this.name = name;
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramType = paramType;
        this.paramName = paramName;
    }

    ContactScreenNameEnum() {
    }

    public static ContactScreenNameEnum getByCode(String code) {

        for (ContactScreenNameEnum value : ContactScreenNameEnum.values()) {
            if(StringUtils.equals(code,value.getCode())){
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }

    public Class getParamName() {
        return paramName;
    }

    public void setParamName(Class paramName) {
        this.paramName = paramName;
    }
}
