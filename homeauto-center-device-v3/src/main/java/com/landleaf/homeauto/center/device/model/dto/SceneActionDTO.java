package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel("场景动作")
public class SceneActionDTO {

    @ApiModelProperty("模式配置ID")
    private String configId;

    @ApiModelProperty(value = "工作模式", required = true)
    private String workMode;

    @ApiModelProperty(value = "风速", required = true)
    private String airSpeed;

    @ApiModelProperty("房间参数")
    private List<RoomParam> roomParams;

    @Data
    @ApiModel("房间参数")
    public static class RoomParam {

        @ApiModelProperty("分室控制配置ID")
        private String roomConfigId;

        @ApiModelProperty("房间ID")
        private String roomId;

        @ApiModelProperty("房间温度")
        private String temperature;
    }
}
