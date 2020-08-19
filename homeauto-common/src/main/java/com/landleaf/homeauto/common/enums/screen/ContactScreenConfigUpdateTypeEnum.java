package com.landleaf.homeauto.common.enums.screen;


/**
 * 云端同步大屏配置数据更新通知类型
 *
 * @author wenyilu
 */
public enum ContactScreenConfigUpdateTypeEnum {

    SCENE("Scene", "户式化场景配置更新"),

    SCENE_NO_SMART("SceneNoSmart", "自由方舟场景配置更新"),

    SCENE_TIMING("SceneTiming", "定时场景配置更新"),

    FLOOR_ROOM_DEVICE("FloorRoomDevice", "楼层房间设备更新"),

    NEWS("News", "消息公告更新"),

    APK_UPDATE("ApkUpdate", "apk版本更新"),
    ;

    public String code;
    public String name;

    ContactScreenConfigUpdateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
