package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/8/13 13:53
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("公告项目信息")
public class ProjectDTO {

    @ApiModelProperty(value = "项目ID",required = true)
    private String projectId;

    @ApiModelProperty( value = "项目名称", required = true)
    private String projectName;

    @ApiModelProperty( value = "消息target的id")
    private String targetId;
}
