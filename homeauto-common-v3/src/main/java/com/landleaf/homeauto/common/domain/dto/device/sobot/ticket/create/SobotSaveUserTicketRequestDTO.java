package com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @ClassName SobotSaveUserTicketRequestDTO
 * @Description: 创建订单-客户
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotSaveUserTicketRequestDTO {


    @ApiModelProperty(value = "企业ID", name = "companyid", required = true, dataType = "String")
    private String companyid;

    @ApiModelProperty(value = "工单标题", name = "ticket_title", required = true, dataType = "String", example = "工单标题")
    private String ticket_title;

    @ApiModelProperty(value = "客户ID", name = "userid", required = false, dataType = "String", example = "客户ID")
    private String userid;

    @ApiModelProperty(value = "对接ID", name = "partnerid", required = false, dataType = "String", example = "对接ID")
    private String partnerid;

    @ApiModelProperty(value = "工单问题描述", name = "ticket_content", required = true, dataType = "String", example = "工单问题描述")
    private String ticket_content;

    @ApiModelProperty(value = "客户邮箱", name = "user_emails", required = false, dataType = "String", example = "客户邮箱")
    private String user_emails;

    @ApiModelProperty(value = "客户电话", name = "user_tels", required = false, dataType = "String", example = "客户电话")
    private String user_tels;

    @ApiModelProperty(value = "工单分类ID", name = "ticket_typeid", required = true, dataType = "String", example = "叶子节点的分类ID")
    private String ticket_typeid;

    @ApiModelProperty(value = "工单来源", name = "ticket_from", required = true, dataType = "String", example = "1 PC客户留言，2 H5客户留言，3 微信公众号客户留言，4 APP客户留言，12 邮件留言，13语音留言，16微信小程序客户留言，17企业微信客户留言")
    private String ticket_from;

    @ApiModelProperty(value = "附件路径", name = "file_str", required = false, dataType = "String", example = "\t多个附件，附件之间采用英文分号\";\"隔开")
    private String file_str;

    @ApiModelProperty(value = "工单自定义字段信息", name = "extend_fields", required = false, dataType = "String", example = "工单自定义字段信息")
    private List<SobotSaveUserTicketExtendFieldDTO> extend_fields;
}
