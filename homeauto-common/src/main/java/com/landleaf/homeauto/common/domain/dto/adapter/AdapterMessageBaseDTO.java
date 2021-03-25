package com.landleaf.homeauto.common.domain.dto.adapter;

import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageSourceEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import lombok.Data;

/**
 * 适配器数据装配
 *
 * @author wenyilu
 */
@Data
public class AdapterMessageBaseDTO {
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
//    /**
//     * 终端类型
//     * {@link TerminalTypeEnum}
//     */
//    private Integer terminalType;
    /**
     * 消息名称
     * {@link AdapterMessageNameEnum}
     */
    private String messageName;
    /**
     * 时间戳
     */
    private Long time;
    /**
     * 来源
     */
    private String source;


}
