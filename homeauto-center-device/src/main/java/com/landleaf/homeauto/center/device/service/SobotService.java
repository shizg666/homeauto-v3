package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeautoFaultReport;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;

import java.util.List;

public interface SobotService {

    String getTicketToken();
    /**
     * 从远程获取工单中心-token
     * @return
     */
    SobotTokenResponseDTO getTicketTokenFromRemote();

    /**
     * 从远程获取工单中心-字典
     * @return
     */
    SobotDataDicResponseDTO getTicketDicDataRemote();

    /**
     * 获取约定的工单分类
     * @return
     */
    String getDefaultTypeId();

    /**
     * 获取报修现象的字段id
     * @param typeId
     * @return
     */
    String getRepirFieldId(String typeId);

    SobotQueryFieldsByTypeIdResponseDTO queryTicketFiledsByTypeid(String typeId);

    /**
     * 保存扩展字段可选值
     * @param saveDatas
     */
    void saveExtendFieldOptions(List<SobotTicketTypeFiledOption> saveDatas);

    List<SobotTicketTypeFiledOption> getRepirApperanceOptions();

    HomeautoFaultReport createUserTicket(String description, String repairAppearance,String phone);
}
