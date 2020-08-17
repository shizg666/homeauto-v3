package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 工单详情
 *
 * @author wenyilu
 */
@Data
public class SobotTicketDetailDTO {

    @ApiModelProperty(value = "公司ID", name = "companyid", required = true, notes = "")
    private String companyid;
    @ApiModelProperty(value = "工单ID", name = "ticketid", required = true, notes = "")
    private String ticketid;

    @ApiModelProperty(value = "工单标题", name = "ticket_title", required = true, notes = "")
    private String ticket_title;

    @ApiModelProperty(value = "\t工单分类", name = "titicket_type_name", required = true, notes = "显示格式：一级/二级/三级")
    private String titicket_type_name;

    @ApiModelProperty(value = "问题描述", name = "ticket_content", required = true, notes = "")
    private String ticket_content;
    @ApiModelProperty(value = "工单编号\t", name = "ticket_code", required = true, notes = "")
    private String ticket_code;
    @ApiModelProperty(value = "工单来源", name = "ticket_from", required = true, notes = "工单中心，1 PC客户留言，2 H5客户留言，3 微信公众号客户留言，4 APP客户留言，6 PC-在线工作台，7客户中心，8呼叫中心，9微信公众号-在线工作台，10 H5-在线工作台，11 APP-在线工作台，12 邮件留言，13语音留言，14微信小程序-在线工作台，15企业微信-在线工作台，16微信小程序客户留言，17企业微信客户留言")
    private String ticket_from;
    @ApiModelProperty(value = "工单优先级", name = "ticket_level", required = true, notes = "0低，1中，2高，3紧急")
    private String ticket_level;
    @ApiModelProperty(value = "工单状态", name = "ticket_status", required = true, notes = "0尚未受理，1受理中，2等待回复，3已解决，99已关闭，98已删除")
    private String ticket_status;
    @ApiModelProperty(value = "工单发起人类型", name = "start_type", required = true, notes = "0坐席，1客户")
    private String start_type;
    @ApiModelProperty(value = "工单发起人名称\t", name = "start_name", required = true, notes = "")
    private String start_name;
    @ApiModelProperty(value = "理坐席名称", name = "deal_agent_name", required = true, notes = "")
    private String deal_agent_name;
    @ApiModelProperty(value = "受理技能组名称", name = "deal_group_name", required = true, notes = "")
    private String deal_group_name;
    @ApiModelProperty(value = "工单创建时间", name = "create_datetime", required = true, notes = "2018-09-18 12:00:00")
    private String create_datetime;
    @ApiModelProperty(value = "工单更新时间", name = "update_datetime", required = true, notes = "")
    private String update_datetime;
    @ApiModelProperty(value = "客户昵称", name = "user_nick", required = true, notes = "")
    private String user_nick;
    @ApiModelProperty(value = "客户名称", name = "user_name", required = true, notes = "")
    private String user_name;
    @ApiModelProperty(value = "客户电话", name = "user_tels", required = true, notes = "多个电话号码，号码之间采用英文逗号\",\"隔开")
    private String user_tels;
    @ApiModelProperty(value = "客户邮箱", name = "user_emails", required = true, notes = "多个邮箱，邮箱之间采用英文逗号\",\"隔开")
    private String user_emails;
    @ApiModelProperty(value = "工单更新人名称", name = "update_agent_name", required = true, notes = "")
    private String update_agent_name;
    @ApiModelProperty(value = "工单自定义字段", name = "extend_fields_list", required = true, notes = "")
    private List<SobotTicketDetailExtendFieldDTO> extend_fields_list;
    @ApiModelProperty(value = "工单全部回复\t", name = "deal_list", required = true, notes = "")
    private List<SobotTicketDetailDealDTO> deal_list;
    @ApiModelProperty(value = "工单操作记录", name = "update_log_list", required = true, notes = "")
    private List<SobotTicketDetailLogDTO> update_log_list;


}
