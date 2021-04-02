package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SobotQueryTicketResponseDTO
 * @Description: 查询工单
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotQueryTicketResponseDTO extends SobotBaseResponseDTO {


    private List<SobotTicketDTO> items;

    private Integer page_no;
    private Integer page_size;
    private Integer page_count;
    private Integer total_count;
}
