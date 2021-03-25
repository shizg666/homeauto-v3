package com.landleaf.homeauto.common.domain.dto.adapter;

import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import lombok.Data;

/**
 * 适配器数据装配
 *
 * @author wenyilu
 */
@Data
public class AdapterMessageHttpDTO {
    /**
     * 家庭id
     */
    private String familyId;
    /**
     * 家庭code
     */
    private String familyCode;
    /**
     * 终端 地址
     */
    private String terminalMac;
    /**
     * 终端类型
     * {@link TerminalTypeEnum}
     */
    private Integer terminalType;
    /**
     * 时间戳
     */
    private Long time;


}
