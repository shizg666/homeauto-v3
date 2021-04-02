package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketRequestDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicket;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-27
 */
public interface ISobotTicketService extends IService<SobotTicket> {

    void saveTicket(SobotSaveUserTicketRequestDTO requestDTO, String ticketId);

    void updateStatusByTicketId(String ticketid, Integer ticket_status);
}
