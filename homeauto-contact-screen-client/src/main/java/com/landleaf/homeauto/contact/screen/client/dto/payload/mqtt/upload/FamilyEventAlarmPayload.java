package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContaceScreenAlarmMsgItem;
import lombok.Data;

import java.util.List;

/**
 * 大屏报警事件上传
 *
 * @author wenyilu
 */
@Data
public class FamilyEventAlarmPayload {

    /**
     * 报警设备设备号
     */
    private String deviceSn;
    /**
     * 详细信息
     */
    List<ContaceScreenAlarmMsgItem> data;


}
