package com.landleaf.homeauto.center.websocket.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.websocket.model.DeviceStatusDTO;
import com.landleaf.homeauto.center.websocket.model.vo.DeviceStatusVO;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import com.landleaf.homeauto.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

/**
 * @author Yujiumin
 * @version 2020/9/3
 */
@Slf4j
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {

    @Autowired
    private Map<String, WebSocketSession> familySessionMap;

    @PostMapping("/push")
    public void push(@RequestBody List<DeviceStatusDTO> deviceStatusDTOList) {
        // 先按家庭分类
        Map<String, List<DeviceStatusDTO>> familyDeviceMap = new LinkedHashMap<>();
        for (DeviceStatusDTO deviceStatusDTO : deviceStatusDTOList) {
            String key = deviceStatusDTO.getFamilyId();
            if (familyDeviceMap.containsKey(key)) {
                familyDeviceMap.get(key).add(deviceStatusDTO);
            } else {
                familyDeviceMap.put(key, CollectionUtil.list(true, deviceStatusDTO));
            }
        }

        // 按家庭推送设备状态
        for (String familyId : familyDeviceMap.keySet()) {
            List<DeviceStatusDTO> deviceStatusList = familyDeviceMap.get(familyId);
            Map<String, Map<String, Object>> deviceStatusMap = new LinkedHashMap<>();
            for (DeviceStatusDTO deviceStatusDTO : deviceStatusList) {
                String key = deviceStatusDTO.getCategory() + ":" + deviceStatusDTO.getDeviceSn();
                if (deviceStatusMap.containsKey(key)) {
                    deviceStatusMap.get(key).put(deviceStatusDTO.getAttributeName(), deviceStatusDTO.getAttributeValue());
                } else {
                    Map<String, Object> attribute = new LinkedHashMap<>();
                    attribute.put(deviceStatusDTO.getAttributeName(), deviceStatusDTO.getAttributeValue());
                    deviceStatusMap.put(key, attribute);
                }
            }

            // 组装家庭里的设备数据
            List<DeviceStatusVO> deviceStatusVOList = new LinkedList<>();
            for (String key : deviceStatusMap.keySet()) {
                String[] keySplit = key.split(":");
                DeviceStatusVO deviceStatusVO = new DeviceStatusVO();
                deviceStatusVO.setCategory(keySplit[0]);
                deviceStatusVO.setDeviceSn(keySplit[1]);
                deviceStatusVO.setAttributes(deviceStatusMap.get(key));
                deviceStatusVOList.add(deviceStatusVO);
            }

            // 推送
            WebSocketSession webSocketSession = familySessionMap.get(familyId);
            if (!Objects.isNull(webSocketSession)) {
                MessageUtils.sendMessage(webSocketSession, deviceStatusVOList);
            } else {
                log.error("推送失败,家庭[{}]不在线", familyId);
            }

        }
    }

}
