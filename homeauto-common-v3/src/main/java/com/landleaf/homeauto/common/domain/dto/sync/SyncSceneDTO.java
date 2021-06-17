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
@ApiModel(value="SyncSceneDTO", description="场景同步")
public class SyncSceneDTO {

    @ApiModelProperty("属性配置")
    private List<SyncSceneActionDTO> attrs;

    @ApiModelProperty("设备号")
    private Integer deviceSn;

    @ApiModelProperty("产品编号")
    private String productCode;

}
