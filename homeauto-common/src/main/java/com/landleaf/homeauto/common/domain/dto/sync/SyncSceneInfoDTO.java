package com.landleaf.homeauto.common.domain.dto.sync;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/29 10:06
 * @description: 邮箱信息
 */
@Data
@ToString
@ApiModel(value="SyncSceneInfoDTO", description="SyncSceneInfoDTO")
public class SyncSceneInfoDTO {

    @ApiModelProperty("是否可修改")
    private Integer upateFlag;

    @ApiModelProperty("场景名称")
    private String name;

    @ApiModelProperty("场景id")
    private String id;

    @ApiModelProperty("场景动作")
    private List<SyncSceneDTO> action;

}
