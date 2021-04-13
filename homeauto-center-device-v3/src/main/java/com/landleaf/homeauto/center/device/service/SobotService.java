package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReport;
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

    /**
     * 创建报修记录
     * @param deviceName   设备名称
     * @param contentCode      故障内容--对应客服平台报修现象
     * @param familyId    家庭Id
     * @param phone       手机号
     * @param userId      用户ID
     * @return
     */
    HomeAutoFaultReport createUserTicket(String deviceName, String contentCode, Long familyId, String phone, String userId);
}
