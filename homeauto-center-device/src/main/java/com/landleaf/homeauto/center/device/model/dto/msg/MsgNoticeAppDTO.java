package com.landleaf.homeauto.center.device.model.dto.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/13 14:04
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("MsgNoticeAppDTO公告消息对象")
public class MsgNoticeAppDTO implements Serializable {

    private static final long serialVersionUID = 1490029160840769319L;

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty( value = "标题", required = true)
    private String name;

    @ApiModelProperty( value = "正文", required = true)
    private String content;

    @ApiModelProperty("是否已读 0否 1是")
    private Integer readFalg = 0;

    @ApiModelProperty("类型")
    private Integer msgType;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime releaseTime;
}
