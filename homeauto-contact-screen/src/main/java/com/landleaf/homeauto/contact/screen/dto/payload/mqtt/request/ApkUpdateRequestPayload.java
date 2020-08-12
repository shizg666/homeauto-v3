package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * apk请求响应payload
 *
 * @author wenyilu
 */
@Builder
@Data
public class ApkUpdateRequestPayload {

    /**
     * apk升级包存放地址
     */
    private String url;

    /**
     * 当前版本号
     */
    private String version;


}
