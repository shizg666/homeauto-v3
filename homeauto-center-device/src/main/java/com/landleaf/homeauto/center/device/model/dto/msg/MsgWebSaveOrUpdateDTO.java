package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "MsgWebSaveOrUpdateDTO",description = "Web消息新增和修改对象")
@Data
@NoArgsConstructor
@ToString
public class MsgWebSaveOrUpdateDTO implements Serializable {

    @ApiModelProperty( value = "标题", required = true)
    private String name;

    @ApiModelProperty( value = "正文", required = true)
    private String content;

    @ApiModelProperty("楼盘id")
    private String realestateId;

    @ApiModelProperty("楼盘名称")
    private String realestateName;

    @ApiModelProperty( value = "项目列表", required = true)
    private List<ProjectDTO> projectDTOList;

}
