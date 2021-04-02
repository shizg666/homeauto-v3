package com.landleaf.homeauto.center.adapter.handle.upload;

import com.landleaf.homeauto.center.adapter.service.FamilyParseProvider;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AbstractContactScreenUploadHandle
 * @Description: TODO
 * @Author wyl
 * @Date 2021/3/31
 * @Version V1.0
 **/
@Slf4j
public class AbstractContactScreenUploadHandle {

    public FamilyParseProvider familyParseProvider;

    public void generateBaseFamilyInfo(AdapterMessageUploadDTO message) {
        AdapterFamilyDTO familyDTO = familyParseProvider.getFamily(message.getTerminalMac());
        if (familyDTO == null) {
            log.error("[大屏上报安防报警状态消息]家庭不存在,[终端地址]:{}", message.getTerminalMac());
            return;
        }
        message.setFamilyId(familyDTO.getFamilyId());
        message.setFamilyCode(familyDTO.getFamilyCode());
        message.setHouseTemplateId(familyDTO.getHouseTemplateId());
    }
}
