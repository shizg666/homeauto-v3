package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 回复附件
 *
 * @author wenyilu
 */
@Data
public class SobotTicketDetailFileDTO {

    @ApiModelProperty(value = "附件名称", name = "file_name", required = true, notes = "")
    private String file_name;

    @ApiModelProperty(value = "附件路径", name = "file_url", required = true, notes = "")
    private String file_url;

    @ApiModelProperty(value = "附件类型", name = "file_type", required = true, notes = "附件类型")
    private String file_type;


}
