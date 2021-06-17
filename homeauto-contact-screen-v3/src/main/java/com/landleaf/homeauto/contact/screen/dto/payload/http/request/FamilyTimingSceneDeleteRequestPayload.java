package com.landleaf.homeauto.contact.screen.dto.payload.http.request;

import lombok.Data;

import java.util.List;

/**
 * 定时场景配置删除请求payload
 *
 * @author wenyilu
 */
@Data
public class FamilyTimingSceneDeleteRequestPayload {
    /**
     * 定时配置Id
     */
    private List<Long> request;
}
