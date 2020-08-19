package com.landleaf.homeauto.common.constant;

/**
 * 定义rocketmq通用的常量
 * @author wenyilu
 */
public interface RocketMqConst {

    /************************************内部服务topic定义*********************************************/
    /**
     * 适配器==》大屏通讯模块
     */
    String TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN = "center_adapter_to_contact_screen";
    /**
     * 大屏通讯模块==》适配器
     */
    String TOPIC_CONTACT_SCREENC_TO_ENTER_ADAPTER = "contact_screen_to_center_adapter";









    /************************************tag定义*********************************************/
    /**
     * 读取状态
     */
    String TAG_DEVICE_STATUS_READ ="device_status_read";
    /**
     * 控制设备
     */
    String TAG_DEVICE_WRITE ="device_write";
    /**
     * 配置更新
     */
    String TAG_FAMILY_CONFIG_UPDATE ="family_config_update";
    /**
     * 控制场景
     */
    String TAG_FAMILY_SCENE_SET ="family_scene_set";
    /**
     * 状态上报
     */
    String DEVICE_STATUS_UPLOAD ="device_status_upload";
    /**
     * 大屏控制场景上报
     */
    String SCREEN_APK_UPDATE_CHECK ="screen_apk_update_check";
}
