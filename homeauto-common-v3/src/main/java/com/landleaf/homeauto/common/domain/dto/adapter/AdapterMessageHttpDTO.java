package com.landleaf.homeauto.common.domain.dto.adapter;

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
    private Long familyId;
    /**
     * 户型ID(因为所有业务基于户型)
     */
    private Long houseTemplateId;
    /**
     * 家庭code
     */
    private String familyCode;
    /**
     * 终端 地址
     */
    private String terminalMac;
    /**
     * 时间戳
     */
    private Long time;


}
