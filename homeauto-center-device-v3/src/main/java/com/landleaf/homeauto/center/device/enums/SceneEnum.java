package com.landleaf.homeauto.center.device.enums;

import lombok.Getter;

/**
 * 场景枚举
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
public enum SceneEnum {

    /**
     * 全屋场景
     */
    WHOLE_HOUSE_SCENE("全屋场景", 1),

    /**
     * 智能场景
     */
    SMART_SCENE("智能场景", 2);

    String name;

    @Getter
    Integer type;

    SceneEnum(String name, Integer type) {
        this.name = name;
        this.type = type;
    }
}
