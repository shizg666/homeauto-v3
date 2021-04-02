package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "MsgWebUpdateDTO",description = "Web消息修改对象")
@Data
@NoArgsConstructor
@ToString
public class MsgWebUpdateDTO implements Serializable {

    @ApiModelProperty( value = "消息公告id", required = true)
    private String msgId;

    @ApiModelProperty( value = "标题", required = true)
    private String name;

    @ApiModelProperty( value = "正文", required = true)
    private String content;

    @ApiModelProperty( value = "发布标识", required = true)
    private Integer releaseFlag;

    @ApiModelProperty( value = "推送项目列表", required = true)
    private List<ProjectDTO> projectList;

}
