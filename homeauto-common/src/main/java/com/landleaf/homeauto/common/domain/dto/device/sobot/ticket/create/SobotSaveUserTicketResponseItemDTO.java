package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SobotSaveUserTicketResponseItemDTO
 * @Description: 新增客户工单返回子对象
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotSaveUserTicketResponseItemDTO extends SobotBaseResponseDTO {

    private String  ticketid;
}
