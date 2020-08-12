package com.landleaf.homeauto.common.enums.screen;


/**
 * 云端同步大屏配置数据更新通知类型
 *
 * @author wenyilu
 */
public enum ContactScreenConfigUpdateTypeEnum {
    Scene("Scene", "户式化场景配置更新"),
    SceneNoSmart("SceneNoSmart", "自由方舟场景配置更新"),
    SceneTiming("SceneTiming", "定时场景配置更新"),
    SceneSmart("SceneSmart", "SceneSmart"),
    FloorRoomDevice("FloorRoomDevice", "楼层房间设备更新"),
    News("News", "消息公告更新"),
    ReservationNoSmart("ReservationNoSmart", "自由方舟预约更新通知"),
    ;

    public String code;
    public String name;

    ContactScreenConfigUpdateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
