package com.landleaf.homeauto.common.domain.dto.email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Lokiy
 * @date 2019/8/29 10:06
 * @description: 邮箱信息
 */
@Data
@ToString
@ApiModel("邮箱信息")
public class EmailMsgDTO {

    @ApiModelProperty("目标邮箱")
    private String email;

    @ApiModelProperty("自定义主题")
    private String subject;

    /**
     * 主动发送信息时,需填写
     * 发送邮箱验证码时,不填为null
     * 验证邮箱验证码时,需填写验证的验证码
     */
    @ApiModelProperty("发送信息")
    private String msg;

    @ApiModelProperty("邮件信息类型")
    @NotBlank(message = "邮件信息类型不能为空")
    private Integer emailMsgType;
}
