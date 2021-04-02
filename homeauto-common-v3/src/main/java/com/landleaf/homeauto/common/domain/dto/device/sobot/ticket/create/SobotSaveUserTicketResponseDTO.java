package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SobotQueryFieldsByTypeIdResponseDTO
 * @Description: 查询工单分类关联的工单模板
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotSaveUserTicketResponseDTO extends SobotBaseResponseDTO {

    private SobotSaveUserTicketResponseItemDTO item;
}
