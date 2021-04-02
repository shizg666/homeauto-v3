package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 坐席
 *
 * @author wenyilu
 */
@Data
public class SobotTicketAgentDTO {

    @ApiModelProperty(value = "坐席ID", name = "agentid", required = true, notes = "")
    private String agentid;

    @ApiModelProperty(value = "坐席名称", name = "agent_name", required = true, notes = "")
    private String agent_name;


}
