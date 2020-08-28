package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.SobotTicketMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketService;
import com.landleaf.homeauto.common.constant.enums.FaultReportStatusEnum;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketRequestDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-27
 */
@Service
public class SobotTicketServiceImpl extends ServiceImpl<SobotTicketMapper, SobotTicket> implements ISobotTicketService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTicket(SobotSaveUserTicketRequestDTO requestDTO, String ticketId) {

        SobotTicket ticket = new SobotTicket();
        ticket.setCompanyid(requestDTO.getCompanyid());
        ticket.setExtendFields(JSON.toJSONString(requestDTO.getExtend_fields()));
        ticket.setFileStr(requestDTO.getFile_str());
        ticket.setPartnerid(requestDTO.getPartnerid());
        ticket.setStatus(Integer.parseInt(FaultReportStatusEnum.PENDING.getCode()));
        ticket.setTicketContent(requestDTO.getTicket_content());
        ticket.setTicketFrom(Integer.parseInt(requestDTO.getTicket_from()));
        ticket.setTicketId(ticketId);
        ticket.setTicketTitle(requestDTO.getTicket_title());
        ticket.setTicketTypeid(requestDTO.getTicket_typeid());
        ticket.setUserEmails(requestDTO.getUser_emails());
        ticket.setUserid(requestDTO.getUserid());
        ticket.setUserTels(requestDTO.getUser_tels());
        save(ticket);

    }
}
