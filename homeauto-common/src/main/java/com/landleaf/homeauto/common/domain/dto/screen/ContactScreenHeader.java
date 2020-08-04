package com.landleaf.homeauto.common.domain.dto.screen;

import lombok.Data;

/**
 * 接收请求通用header
 */
@Data
public class ContactScreenHeader {

    /**
     * 指令的类别
     */
    private String namespace;

    /**
     * 指令的名称
     */
    private String name;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 版本号
     */
    private String payloadVersion;

    /**
     * 家庭编码
     */
    private String familyCode;


    public ContactScreenHeader() {
    }

    public ContactScreenHeader(String namespace, String name, String messageId, String payloadVersion, String familyCode) {
        this.namespace = namespace;
        this.name = name;
        this.messageId = messageId;
        this.payloadVersion = payloadVersion;
        this.familyCode = familyCode;
    }
}
