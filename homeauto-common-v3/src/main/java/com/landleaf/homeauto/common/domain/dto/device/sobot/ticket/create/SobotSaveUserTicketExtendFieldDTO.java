package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自定义字段
 *
 * @author wenyilu
 */
@Data
public class SobotSaveUserTicketExtendFieldDTO {

    @ApiModelProperty(value = "自定义字段ID", name = "fieldid", required = true, notes = "")
    private String fieldid;

    @ApiModelProperty(value = "自定义字段值", name = "field_value", required = true, notes = "")
    private String field_value;


}
