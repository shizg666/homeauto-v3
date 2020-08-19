package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 消息公告信息
 **/
@Data
public class ContactScreenNews {

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
