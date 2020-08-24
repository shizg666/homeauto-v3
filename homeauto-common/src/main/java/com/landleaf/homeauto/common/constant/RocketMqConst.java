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
    String TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN = "center_adapter_to_contact_screen";
    /**
     * 适配器==》app模块
     */
    String TOPIC_CENTER_ADAPTER_TO_APP = "center_adapter_to_app";
    /**
     * 大屏通讯模块==》适配器
     */
    String TOPIC_CONTACT_SCREEN_TO_CENTER_ADAPTER = "contact_screen_to_center_adapter";
    /**
     * app模块==》适配器
     */
    String TOPIC_APP_TO_CENTER_ADAPTER = "app_to_center_adapter";
    /**
     * 适配器发布安防报警topic
     */
    String TOPIC_CENTER_ADAPTER_PUB_SECURITY_ALARM_EVENT = "center_adapter_pub_security_alarm_event";
    /**
     * 适配器发布设备状态更新topic
     */
    String TOPIC_CENTER_ADAPTER_PUB_DEVICE_STATUS_UPDATE = "center_adapter_pub_device_status_update";
    /**
     * 适配器发布大屏控制场景topic
     */
    String TOPIC_CENTER_ADAPTER_PUB_SCREEN_SCENE_SET = "center_adapter_pub_screen_scene_set";


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
}
