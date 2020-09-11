package com.landleaf.homeauto.common.domain.dto.sync;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/8/29 10:06
 * @description: 邮箱信息
 */
@Data
@ToString
@ApiModel(value="SyncSceneActionDTO", description="场景设备动作同步对象")
public class SyncSceneActionDTO {

    @ApiModelProperty("属性code")
    private String attrTag;

    @ApiModelProperty("属性值")
    private String attrValue;


}
