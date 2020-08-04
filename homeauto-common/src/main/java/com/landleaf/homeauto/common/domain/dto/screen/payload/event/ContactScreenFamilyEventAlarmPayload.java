package com.landleaf.homeauto.common.domain.dto.screen.payload.event;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 大屏报警事件上传
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyEventAlarmPayload {

    List<Map<String, Object>> items;


}
