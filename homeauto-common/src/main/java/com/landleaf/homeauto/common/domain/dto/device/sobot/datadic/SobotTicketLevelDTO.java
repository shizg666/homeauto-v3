package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工单优先级
 *
 * @author wenyilu
 */
@Data
public class SobotTicketLevelDTO {

    @ApiModelProperty(value = "工单优先级CODE", name = "dict_value", required = true, notes = "")
    private String dict_value;

    @ApiModelProperty(value = "工单优先级名称", name = "dict_name", required = true, notes = "")
    private String dict_name;


}
