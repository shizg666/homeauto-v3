package com.landleaf.homeauto.common.domain.dto.screen.payload.notice;

import lombok.Data;

/**
 * 数据更新通知响应payload
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyConfigUpdateReplyPayload {


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;


}
