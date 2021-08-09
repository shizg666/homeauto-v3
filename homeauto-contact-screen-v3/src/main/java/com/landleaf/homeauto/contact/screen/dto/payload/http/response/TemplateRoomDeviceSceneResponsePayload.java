package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import lombok.Data;

import java.util.List;

/**
 * @Author Lokiy
 * @Date 2021/8/9 14:39
 * @Description 模板房间设备场景返回对象体
 */
@Data
public class TemplateRoomDeviceSceneResponsePayload {

    /**
     * 房间设备列表
     */
    private List<FamilyRoomDeviceResponsePayload> floors;


    /**
     * 场景列表
     */
    private List<FamilySceneResponsePayload> scenes;
}
