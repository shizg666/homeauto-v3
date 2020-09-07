package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "MsgWebSaveDTO",description = "Web消息新增对象")
@Data
@NoArgsConstructor
@ToString
public class MsgWebSaveDTO implements Serializable {

    @ApiModelProperty( value = "标题", required = true)
    private String name;

    @ApiModelProperty( value = "正文", required = true)
    private String content;

    @ApiModelProperty( value = "推送项目列表", required = true)
    private List<ProjectDTO> projectList;

}
