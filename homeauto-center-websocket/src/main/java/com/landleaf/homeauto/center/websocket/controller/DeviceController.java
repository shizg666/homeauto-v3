package com.landleaf.homeauto.center.websocket.controller;

import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import com.landleaf.homeauto.center.websocket.model.DeviceStatusVO;
import com.landleaf.homeauto.center.websocket.model.WebSocketMessageModel;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import com.landleaf.homeauto.common.domain.dto.device.DeviceStatusDTO;
import com.landleaf.homeauto.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * @author Yujiumin
 * @version 2020/9/3
 */
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController {

    @Autowired
    private Map<String, WebSocketSession> familySessionMap;

    @PostMapping("/status/push")
    public void push(@RequestBody DeviceStatusDTO deviceStatusDTO) {
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO();
        deviceStatusVO.setDeviceSn(deviceStatusDTO.getDeviceSn());
        deviceStatusVO.setCategory(deviceStatusDTO.getCategory());
        deviceStatusVO.setAttributes(deviceStatusDTO.getAttributes());

        // 推送
        WebSocketSession webSocketSession = familySessionMap.get(deviceStatusDTO.getFamilyId());
        if (!Objects.isNull(webSocketSession)) {
            MessageUtils.sendMessage(webSocketSession, new WebSocketMessageModel(MessageEnum.DEVICE_STATUS, deviceStatusVO));
        } else {
            log.error("推送失败,家庭[{}]不在线", deviceStatusDTO.getFamilyId());
        }
    }

}
