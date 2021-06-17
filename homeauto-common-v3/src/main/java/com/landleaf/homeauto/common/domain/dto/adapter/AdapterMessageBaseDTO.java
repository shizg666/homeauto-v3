package com.landleaf.homeauto.common.domain.dto.adapter;

import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
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
     * 消息Id
     */
    private String messageId;
    /**
     * 终端 地址
     */
    private String terminalMac;
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

    public void buildBaseInfo(Long familyId,String familyCode,Long houseTemplateId,String terminalMac,Long time){
        this.familyId=familyId;
        this.familyCode=familyCode;
        this.houseTemplateId=houseTemplateId;
        this.terminalMac=terminalMac;
        this.time=time;
    }

}
