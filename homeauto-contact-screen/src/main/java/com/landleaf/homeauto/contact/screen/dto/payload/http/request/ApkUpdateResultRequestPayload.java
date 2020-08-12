package com.landleaf.homeauto.contact.screen.dto.payload.http.request;

import lombok.Data;

/**
 * 大屏更新apk结果回调通知
 *
 * @author wenyilu
 */
@Data
public class ApkUpdateResultRequestPayload {
    /**
     * 当前版本号
     */
    private String version;

}
