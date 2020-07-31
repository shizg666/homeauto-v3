package com.landleaf.homeauto.common.domain.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息模板传输对象
 *
 * @author Yujiumin
 * @version 2020/7/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("消息模板数据传输对象")
public class MessageTemplateDTO {

    @ApiModelProperty("主题信息")
    private String subject;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty(value = "过期时间", notes = "单位: 秒-s, 分-m, 时-h, 天-d, 月-M", example = "5m")
    private String ttl;

    @ApiModelProperty(value = "消息类型", notes = "例如,区分短信消息或者是邮件消息")
    private Integer type;

    @ApiModelProperty("推送方式")
    private Integer pushWay;

    @ApiModelProperty(value = "操作者", notes = "指的是执行当前操作的用户的用户名")
    private String operator;

}
