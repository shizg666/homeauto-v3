package com.landleaf.homeauto.center.device.model.dto.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Lokiy
 * @date 2019/8/13 14:04
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("MsgReadNoteDTO")
public class MsgReadNoteDTO implements Serializable {


    private static final long serialVersionUID = 6863440960863796940L;
    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty( value = "类型 消息公告传 1")
    private String msgType;

    @ApiModelProperty(value = "用户id 非必填")
    private String userId;

}
