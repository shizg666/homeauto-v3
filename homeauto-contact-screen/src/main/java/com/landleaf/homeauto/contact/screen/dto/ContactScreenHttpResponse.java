package com.landleaf.homeauto.contact.screen.dto;

import lombok.Data;

/**
 * 大屏通过http与云端交互实体
 *
 * @author wenyilu
 */
@Data
public class ContactScreenHttpResponse<T> {
    /**
     * 特定参数
     */
    private T data;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;
}
