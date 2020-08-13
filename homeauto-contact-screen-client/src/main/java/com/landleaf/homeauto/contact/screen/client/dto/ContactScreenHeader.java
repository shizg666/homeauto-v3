package com.landleaf.homeauto.contact.screen.client.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 接收请求通用header
 * @author wenyilu
 */
@Data
@Builder
public class ContactScreenHeader {


    /**
     * 指令的名称
     */
    private String name;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 家庭编码
     */
    private String familyCode;
    /**
     * 大屏mac
     */
    private String screenMac;
    /**
     * 家庭方案
     */
    private String familyScheme;
    /**
     * 是否需要响应
     */
    private String ackCode;


    public ContactScreenHeader() {
    }

    public ContactScreenHeader(String name, String messageId, String familyCode, String screenMac, String familyScheme, String ackCode) {
        this.name = name;
        this.messageId = messageId;
        this.familyCode = familyCode;
        this.screenMac = screenMac;
        this.familyScheme = familyScheme;
        this.ackCode = ackCode;
    }
}
