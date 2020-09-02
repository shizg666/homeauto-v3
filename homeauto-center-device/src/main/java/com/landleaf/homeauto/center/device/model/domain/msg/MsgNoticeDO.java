package com.landleaf.homeauto.center.device.model.domain.msg;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description 1.0
 * @Author zhanghongbin
 * @Date 2020/9/1 10:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("msg_notice")
@ApiModel(value="MsgNoticeDO", description="消息表")
public class MsgNoticeDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "终端类型 0-所有 1-app 2-大屏")
    private Integer terminalType;

    @ApiModelProperty(value = "发布标识 0-未发布 1-已发布")
    private Integer releaseFlag;


    @ApiModelProperty(value = "公告标题")
    private String name;

    @ApiModelProperty(value = "公告内容")
    private String content;


    @ApiModelProperty(value = "发布人")
    private String releaseUser;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "发送时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime sendTime;


}
