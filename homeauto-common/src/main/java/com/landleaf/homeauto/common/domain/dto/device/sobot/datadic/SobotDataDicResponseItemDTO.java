package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * token请求返回对象
 *
 * @author wenyilu
 */
@Data
public class SobotDataDicResponseItemDTO {

    @ApiModelProperty(value = "工单分类信息", name = "ticket_type_list", required = true, notes = "企业自定义分类，业务变化频繁")
    private List<SobotTicketTypeDTO> ticket_type_list;

    @ApiModelProperty(value = "工单状态信息", name = "ticket_type_list", required = true, notes = "")
    private List<SobotTicketStatusDTO> ticket_status_list;

    @ApiModelProperty(value = "工单优先级信息", name = "ticket_type_list", required = true, notes = "")
    private List<SobotTicketLevelDTO> ticket_level_list;

    @ApiModelProperty(value = "技能组信息", name = "ticket_type_list", required = true, notes = "业务变化频繁")
    private List<SobotTicketGroupDTO> group_list;

    @ApiModelProperty(value = "坐席信息", name = "ticket_type_list", required = true, notes = "业务变化频繁")
    private List<SobotTicketAgentDTO> agent_list;


}
