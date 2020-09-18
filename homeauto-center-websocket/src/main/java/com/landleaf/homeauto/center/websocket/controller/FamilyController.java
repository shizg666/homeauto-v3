package com.landleaf.homeauto.center.websocket.controller;

import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import com.landleaf.homeauto.common.domain.dto.device.family.FamilyAuthStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Slf4j
@RestController
@RequestMapping("/family")
public class FamilyController {

    @PostMapping("/auth/status")
    public void push(@RequestBody FamilyAuthStatusDTO familyAuthStatusDTO) {
        WebSocketSession webSocketSession = WebSocketSessionContext.get(familyAuthStatusDTO.getFamilyId());
        if (!Objects.isNull(webSocketSession)) {
            MessageUtils.sendMessage(webSocketSession, new MessageModel(MessageEnum.FAMILY_AUTH, familyAuthStatusDTO.getStatus()));
        } else {
            log.error("推送失败,家庭[{}]不在线", familyAuthStatusDTO.getFamilyId());
        }
    }

}
