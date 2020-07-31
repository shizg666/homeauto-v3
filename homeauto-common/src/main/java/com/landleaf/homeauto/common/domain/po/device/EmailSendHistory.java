package com.landleaf.homeauto.common.domain.po.device;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 邮件发送消息记录
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="EmailSendHistory对象", description="邮件发送消息记录")
public class EmailSendHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件消息模板id 无模板-0")
    private String templateId;

    @ApiModelProperty(value = "目标邮箱")
    private String email;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;


}
