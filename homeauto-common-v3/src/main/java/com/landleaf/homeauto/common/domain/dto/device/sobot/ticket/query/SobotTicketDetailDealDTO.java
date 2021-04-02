package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 回复
 *
 * @author wenyilu
 */
@Data
public class SobotTicketDetailDealDTO {

    @ApiModelProperty(value = "回复标题\t", name = "reply_title", required = true, notes = "")
    private String reply_title;

    @ApiModelProperty(value = "回复内容\t", name = "reply_content", required = true, notes = "")
    private String reply_content;

    @ApiModelProperty(value = "回复类型\t", name = "reply_type", required = true, notes = "0 所有人可见 1 仅坐席所见")
    private String reply_type;

    @ApiModelProperty(value = "回复时间\t", name = "reply_datetime", required = true, notes = "2018-09-18 10:34:45")
    private String reply_datetime;

    @ApiModelProperty(value = "回复人类型", name = "start_type", required = true, notes = "0 坐席 1 客户")
    private String start_type;

    @ApiModelProperty(value = "回复人名称\t", name = "update_user_name", required = true, notes = "")
    private String update_user_name;
    @ApiModelProperty(value = "回复人头像", name = "face_img", required = true, notes = "")
    private String face_img;
    @ApiModelProperty(value = "回复附件", name = "file_list", required = true, notes = "")
    private List<SobotTicketDetailFileDTO> file_list;


}
