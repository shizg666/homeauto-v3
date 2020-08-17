package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作记录
 *
 * @author wenyilu
 */
@Data
public class SobotTicketDetailLogDTO {

    @ApiModelProperty(value = "操作记录标题", name = "update_title", required = true, notes = "")
    private String update_title;

    @ApiModelProperty(value = "操作记录内容", name = "update_content", required = true, notes = "")
    private String update_content;

    @ApiModelProperty(value = "操作记录来源", name = "log_from", required = true, notes = "1 页面操作，2 流转触发器，3 定时触发器")
    private String log_from;

    @ApiModelProperty(value = "操作记录时间", name = "reply_datetime", required = true, notes = "2018-09-18 10:34:45")
    private String update_datetime;

    @ApiModelProperty(value = "操作人类型\t", name = "start_type", required = true, notes = "0 坐席 1 客户")
    private String start_type;

    @ApiModelProperty(value = "操作人名称", name = "update_agent_name", required = true, notes = "")
    private String update_agent_name;
    @ApiModelProperty(value = "操作人头像", name = "face_img", required = true, notes = "")
    private String face_img;


}
