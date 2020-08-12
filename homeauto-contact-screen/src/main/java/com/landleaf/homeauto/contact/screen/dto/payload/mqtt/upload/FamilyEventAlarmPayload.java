package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.dto.payload.ContaceScreenAlarmMsgItem;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
