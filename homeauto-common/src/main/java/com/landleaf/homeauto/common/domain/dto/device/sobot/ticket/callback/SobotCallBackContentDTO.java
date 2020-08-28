package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.callback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @ClassName SobotCallBackContentDTO
 * @Description: 工单消息推送
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotCallBackContentDTO {

    @ApiModelProperty(value = "工单标题", name = "ticket_title", required = true, dataType = "String", example = "工单标题")
    private String ticket_title;

    @ApiModelProperty(value = "附件路径", name = "file_str", required = false, dataType = "String", example = "\t多个附件，附件之间采用英文分号\";\"隔开")
    private String file_str;
    @ApiModelProperty(value = "工单编码", name = "ticket_code", required = false, dataType = "String", example = "20200827000019")
    private String ticket_code;
    @ApiModelProperty(value = "客户ID", name = "userid", required = false, dataType = "String", example = "862e00cb9adc4a1a8001878514c7a2e1")
    private String userid;
    @ApiModelProperty(value = "操作人名称", name = "update_name", required = false, dataType = "String", example = "刘晨")
    private String update_name;
    @ApiModelProperty(value = "工单问题描述", name = "ticket_content", required = true, dataType = "String", example = "工单问题描述")
    private String ticket_content;
    @ApiModelProperty(value = "用户手机号", name = "user_tels", required = true, dataType = "String", example = "用户手机号")
    private String user_tels;
    @ApiModelProperty(value = "企业ID", name = "companyid", required = true, dataType = "String")
    private String companyid;
    @ApiModelProperty(value = "操作时间", name = "update_time", required = true, dataType = "String", example = "2020-08-28 09:47:17")
    private String update_time;
    @ApiModelProperty(value = "坐席ID", name = "deal_agentid", required = true, dataType = "String", example = "坐席ID")
    private String deal_agentid;

    @ApiModelProperty(value = "坐席名称\t", name = "create_agent_name", required = true, dataType = "String", example = "坐席名称\t")
    private String deal_agent_name;
    @ApiModelProperty(value = "坐席组", name = "deal_groupid", required = true, dataType = "String", example = "坐席组")
    private String deal_groupid;
    @ApiModelProperty(value = "对接ID", name = "partnerid", required = true, dataType = "String", example = "对接ID")
    private String partnerid;
    @ApiModelProperty(value = "工单ID", name = "ticketid", required = true, dataType = "String", example = "工单ID")
    private String ticketid;
    @ApiModelProperty(value = "工单状态", name = "ticket_status", required = true, dataType = "Integer", example = "工单状态")
    private Integer ticket_status;
    @ApiModelProperty(value = "工单优先级", name = "ticket_level", required = true, dataType = "String", example = "0低，1中，2高，3紧急")
    private Integer ticket_level;
    @ApiModelProperty(value = "工单分类ID", name = "ticket_typeid", required = true, dataType = "String", example = "叶子节点的分类ID")
    private String ticket_typeid;
    @ApiModelProperty(value = "处理结果", name = "reply_content", required = true, dataType = "String", example = "处理结果")
    private String reply_content;

}
