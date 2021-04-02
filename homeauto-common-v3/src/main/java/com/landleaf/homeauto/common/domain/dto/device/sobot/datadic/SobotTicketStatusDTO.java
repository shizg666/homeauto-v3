package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工单状态信息
 *
 * @author wenyilu
 */
@Data
public class SobotTicketStatusDTO {

    @ApiModelProperty(value = "工单状态CODE", name = "dict_value", required = true, notes = "")
    private String dict_value;

    @ApiModelProperty(value = "工单状态名称\n", name = "dict_name", required = true, notes = "")
    private String dict_name;


}
