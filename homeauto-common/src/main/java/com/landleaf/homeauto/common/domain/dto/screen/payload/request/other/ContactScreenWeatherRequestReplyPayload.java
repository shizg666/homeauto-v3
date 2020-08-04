package com.landleaf.homeauto.common.domain.dto.screen.payload.request.other;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 天气信息请求响应
 *
 * @author wenyilu
 */
@Data
public class ContactScreenWeatherRequestReplyPayload {

    /**
     * 数据集合
     */
    private List<Map<String, Object>> items;


}
