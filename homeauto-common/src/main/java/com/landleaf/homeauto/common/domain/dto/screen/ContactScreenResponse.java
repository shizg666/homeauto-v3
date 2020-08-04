package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * 大屏通过mqtt与云端交互实体
 *
 * @author wenyilu
 */
@Data
public class ContactScreenResponse<T> {
    /**
     * 公共参数
     */
    private ContactScreenHeader header;
    /**
     * 特定参数
     */
    private T payload;
}
