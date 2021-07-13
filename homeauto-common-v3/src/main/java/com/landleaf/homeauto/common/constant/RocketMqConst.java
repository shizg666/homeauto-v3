package com.landleaf.homeauto.common.constant;

/**
 * 定义rocketmq通用的常量
 *
 * @author wenyilu
 */
public interface RocketMqConst {

    /************************************内部服务topic定义*********************************************/
    /**
     * 适配器==》大屏通讯模块
     */
    String TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN = "v3_center_adapter_to_contact_screen";
    /**
     * 适配器==》app模块
     */
    String TOPIC_CENTER_ADAPTER_TO_APP = "v3_center_adapter_to_app";
    /**
     * 适配器==》系统重试模块
     */
    String TOPIC_CENTER_ADAPTER_TO_SYSTEM_RETRY = "v3_center_adapter_to_system_retry";
    /**
     * 大屏通讯模块==》适配器
     */
    String TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER = "v3_contact_screen_to_center_adapter";
    /**
     * app模块==》适配器
     */
    String TOPIC_APP_TO_CENTER_ADAPTER = "v3_app_to_center_adapter";
    /**
     * 系统重试模块==》适配器
     */
    String TOPIC_SYSTEM_RETRY_TO_CENTER_ADAPTER = "v3_system_retry_to_center_adapter";

    /**
     * WebSocket模块 -> APP
     */
    String TOPIC_WEBSOCKET_TO_APP = "v3_websocket_to_app";
    /**
     * WebSocket模块 -> applets
     */
    String TOPIC_WEBSOCKET_TO_APPLETS = "v3_websocket_to_applets";
    /**
     * device模块 -> 数据处理服务
     */
    String TOPIC_CENTER_DEVICE_TO_CENTER_DATA = "v3_center_device_to_center_data";


    /************************************tag定义*********************************************/
    /**
     * 读取状态
     */
    String TAG_DEVICE_STATUS_READ = "device_status_read";
    /**
     * 控制设备
     */
    String TAG_DEVICE_WRITE = "device_write";
    /**
     * 配置更新
     */
    String TAG_FAMILY_CONFIG_UPDATE = "family_config_update";
    /**
     * 控制场景
     */
    String TAG_FAMILY_SCENE_SET = "family_scene_set";
    /**
     * 安防报警上报
     */
    String FAMILY_SECURITY_ALARM_EVENT = "family_security_alarm_event";
    /**
     * 状态上报
     */
    String DEVICE_STATUS_UPLOAD = "device_status_upload";
    /**
     * 大屏控制场景上报
     */
    String SCREEN_SCENE_SET_UPLOAD = "screen_scene_set_upload";

    /**
     * 内部服务存储状态
     */
    String TAG_DEVICE_STATUS_TO_DATA = "device_status_to_data";

    /**
     * 暖通故障上报
     */
    String HVAC_FAULT_UPLOAD = "hvac_fault_upload";
    /**
     * 功率上报
     */
    String TAG_POWER_TO_DATA ="power_to_data" ;
}
