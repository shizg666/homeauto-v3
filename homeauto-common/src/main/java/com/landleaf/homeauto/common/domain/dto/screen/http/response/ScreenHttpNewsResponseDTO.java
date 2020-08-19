package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import lombok.Data;

/**
 * 消息公告请求返回
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpNewsResponseDTO {
    /**
     * 消息主键
     */
    private String id;
    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;
    /**
     * 发布人
     */
    private String sender;
    /**
     * 发布时间(yyyy-MM-dd HH:mm:ss)
     */
    private String time;

}
