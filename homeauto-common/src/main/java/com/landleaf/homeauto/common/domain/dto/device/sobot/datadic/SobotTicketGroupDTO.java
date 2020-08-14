package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能组
 *
 * @author wenyilu
 */
@Data
public class SobotTicketGroupDTO {

    @ApiModelProperty(value = "技能组ID", name = "groupid", required = true, notes = "")
    private String groupid;

    @ApiModelProperty(value = "技能组名称", name = "group_name", required = true, notes = "")
    private String group_name;


}
