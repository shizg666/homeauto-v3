package com.landleaf.homeauto.common.domain.dto.adapter;

import lombok.Data;

/**
 * 适配器数据装配
 *
 * @author wenyilu
 */
@Data
public class AdapterMessageUploadDTO {
    /**
     * 家庭id
     */
    private String familyId;
    /**
     * 家庭code
     */
    private String familyCode;
    /**
     * 消息Id
     */
    private String messageId;
    /**
     * 终端 地址
     */
    private String terminalMac;
    /**
     * 终端类型
     */
    private Integer terminalType;
    /**
     * 消息名称
     */
    private String messageName;
    /**
     * 时间
     */
    private Long time;


}
