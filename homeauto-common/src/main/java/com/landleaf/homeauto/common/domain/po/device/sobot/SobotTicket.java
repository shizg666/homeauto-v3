package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SobotTicket对象", description="")
public class SobotTicket extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业id")
    private String companyid;

    @ApiModelProperty(value = "工单标题")
    private String ticketTitle;

    @ApiModelProperty(value = "客户ID")
    private String userid;

    @ApiModelProperty(value = "对接ID")
    private String partnerid;

    @ApiModelProperty(value = "工单问题描述")
    private String ticketContent;

    @ApiModelProperty(value = "客户邮箱")
    private String userEmails;

    @ApiModelProperty(value = "客户电话")
    private String userTels;

    @ApiModelProperty(value = "工单分类ID")
    private String ticketTypeid;

    @ApiModelProperty(value = "工单来源(1 PC客户留言，2 H5客户留言，3 微信公众号客户留言，4 APP客户留言，12 邮件留言，13语音留言，16微信小程序客户留言，17企业微信客户留言)")
    private Integer ticketFrom;

    @ApiModelProperty(value = "附件路径")
    private String fileStr;

    @ApiModelProperty(value = "自定义字段json值")
    private String extendFields;

    @ApiModelProperty(value = "工单id")
    private String ticketId;

    @ApiModelProperty(value = "(0:尚未受理,1:受理中,2:等待回复,3:已解决,4:已关闭)")
    private Integer status;


}
