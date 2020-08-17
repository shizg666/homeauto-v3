package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SobotQueryTicketDetailResponseDTO
 * @Description: 查询工单详情
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotQueryTicketDetailResponseDTO extends SobotBaseResponseDTO {


    private SobotTicketDetailDTO item;
}
