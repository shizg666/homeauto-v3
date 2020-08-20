package com.landleaf.homeauto.contact.screen.common.enums;

import com.landleaf.homeauto.contact.screen.dto.payload.http.request.*;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.DeviceStatusReadRequestReplyPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.DeviceWriteReplyPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.FamilyConfigUpdateReplyPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.response.FamilySceneSetReplyPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.DeviceStatusUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.FamilyEventAlarmPayload;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload.ScreenSceneSetRequestPayload;
import org.apache.commons.lang3.StringUtils;

/**
 * 与大屏通讯指令名称  参数类型对应大屏返回参数类型
 *
 * @author wenyilu
 */
public enum ContactScreenNameEnum {

    /****************************Mqtt协议响应类****************************************/
    DEVICE_WRITE("DeviceWrite", "设备写入", "deviceWritResponseHandle", "handlerRequest", 1, DeviceWriteReplyPayload.class),

    FAMILY_SCENE_SET("FamilySceneSet", "控制场景", "familySceneSetResponseHandle", "handlerRequest", 1, FamilySceneSetReplyPayload.class),

    DEVICE_STATUS_READ("DeviceStatusRead", "读取状态", "deviceStatusReadResponseHandle", "handlerRequest", 1, DeviceStatusReadRequestReplyPayload.class),

    FAMILY_CONFIG_UPDATE("FamilyConfigUpdate", "配置数据更新通知", "familyConfigUpdateResponseHandle", "handlerRequest", 1, FamilyConfigUpdateReplyPayload.class),


    /****************************Mqtt协议上报类****************************************/
    SCREEN_SCENE_SET_UPLOAD("ScreenSceneSetUpload", "大屏上报控制场景", "screenSceneSetUploadHandle", "handlerRequest", 1, ScreenSceneSetRequestPayload.class),

    DEVICE_STATUS_UPDATE("DeviceStatusUpdate", "设备状态更新", "deviceStatusUpdateHandle", "handlerRequest", 1, DeviceStatusUpdateRequestPayload.class),

    FAMILY_SECURITY_ALARM_EVENT("FamilySecurityAlarmEvent", "报警信息上报", "familyDeviceAlarmEventHandle", "handlerRequest", 1, FamilyEventAlarmPayload.class),


    /****************************Http协议类****************************************/
    FAMILY_SCENE_REQUEST("Scene", "场景信息请求", "familySceneRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    FAMILY_SCENE_NON_SMART_REQUEST("SceneNoSmart", "自由方舟场景信息请求", "familyNonSmartSceneRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    FAMILY_SCENE_TIMING_CONFIG_REQUEST("SceneTiming", "定时场景信息请求", "sceneTimingRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    FAMILY_FLOOR_ROOM_DEVICE_REQUEST("FloorRoomDevice", "楼层房间设备信息请求", "familyFloorRoomDeviceRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    FAMILY_NEWS_REQUEST("News", "消息公告信息请求", "familyNewsRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    FAMILY_WEATHER_REQUEST("Weather", "查询天气", "weatherRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    FAMILY_FAMILY_CODE_REQUEST("FamilyCode", "查询家庭码", "familyCodeRequestHandle", "handlerRequest", 1, CommonHttpRequestPayload.class),

    TIMING_SCENE_SAVE_UPDATE("TimingSceneSaveOrUpdate", "定时场景（自由方舟）修改/新增", "sceneTimingSaveOrUpdateRequestHandle", "handlerRequest", 1, FamilyTimingSceneSaveOrUpdateRequestPayload.class),

    TIMING_SCENE_DELETE("TimingSceneDelete", "定时场景（自由方舟）删除", "sceneTimingDeleteRequestHandle", "handlerRequest", 1, FamilyTimingSceneDeleteRequestPayload.class),

    NON_SMART_SCENE_SAVE_UPDATE("NonSmartSceneSaveOrUpdate", "场景（自由方舟）修改/新增", "familyNonSmartSceneSaveOrUpdateRequestHandle", "handlerRequest", 1, FamilyNonSmartSceneRequestSaveOrUpdateRequestPayload.class),

    NON_SMART_SCENE_DELETE("NonSmartSceneDelete", "场景（自由方舟）删除", "familyNonSmartSceneDeleteRequestHandle", "handlerRequest", 1, FamilyNonSmartSceneDeleteRequestPayload.class),

    HOLIDAYS_CHECK("HolidaysCheck", "判断是否是节假日", "holidaysCheckRequestHandle", "handlerRequest", 1, HolidaysCheckRequestPayload.class),

    SCREEN_APK_UPDATE_CHECK("ScreenApkUpdateCheck", "大屏apk更新检测", "apkVersionCheckRequestHandle", "handlerRequest", 1, ApkUpdateResultRequestPayload.class),

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
            if (StringUtils.equals(code, value.getCode())) {
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
