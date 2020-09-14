package com.landleaf.homeauto.center.websocket.controller;

import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import com.landleaf.homeauto.center.websocket.model.DeviceStatusVO;
import com.landleaf.homeauto.center.websocket.model.WebSocketMessageModel;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import com.landleaf.homeauto.common.domain.dto.device.family.FamilyAuthStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Slf4j
@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private Map<String, WebSocketSession> familySessionMap;

    @PostMapping("/auth/status")
    public void push(@RequestBody FamilyAuthStatusDTO familyAuthStatusDTO) {
        WebSocketSession webSocketSession = familySessionMap.get(familyAuthStatusDTO.getFamilyId());
        if (!Objects.isNull(webSocketSession)) {
            MessageUtils.sendMessage(webSocketSession, new WebSocketMessageModel(MessageEnum.FAMILY_AUTH, familyAuthStatusDTO.getStatus()));
        } else {
            log.error("推送失败,家庭[{}]不在线", familyAuthStatusDTO.getFamilyId());
        }
    }

}
