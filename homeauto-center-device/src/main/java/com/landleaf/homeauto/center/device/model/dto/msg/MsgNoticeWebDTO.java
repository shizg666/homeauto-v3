package com.landleaf.homeauto.center.device.model.dto.msg;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/13 14:04
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("公告消息对象")
public class MsgNoticeWebDTO implements Serializable {

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty( value = "标题", required = true)
    private String name;

    @ApiModelProperty( value = "正文", required = true)
    private String content;

    @ApiModelProperty("发布人")
    private String releaseUser;


    @ApiModelProperty( value = "项目列表", required = true)
    private List<ProjectDTO> projectDTOList;


    @ApiModelProperty( value = "发布标识", required = true)
    private Integer releaseFlag;

    @ApiModelProperty("发布时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime releaseTime;
}
