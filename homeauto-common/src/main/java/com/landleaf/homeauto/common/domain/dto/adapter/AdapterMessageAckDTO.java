package com.landleaf.homeauto.common.domain.dto.adapter;

import lombok.Data;

/**
 * 适配器数据装配
 *
 * @author wenyilu
 */
@Data
public class AdapterMessageAckDTO {
    /**
     * 家庭id
     */
    private String familyId;
    /**
     * 户型ID(因为所有业务基于户型)
     */
    private String houseTemplateId;
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
     * 消息名称
     */
    private String messageName;
    /**
     * 时间
     */
    private Long time;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;
    /**
     * 来源
     */
    private String source;


}
