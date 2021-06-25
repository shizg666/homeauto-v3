package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="JZSceneConfigDataVO", description="场景配置数据获取")
public class JZSceneConfigDataVO {

    @ApiModelProperty(value = "房间数据")
    List<JZSceneConfigRoomDataVO> rooms;

    @ApiModelProperty(value = "房间数据")
    List<JZSceneConfigRoomDataVO> rooms1;

    @Data
    @ApiModel(value="JZSceneConfigRoomDataVO", description="房间数据")
    public static class JZSceneConfigRoomDataVO{
        @ApiModelProperty(value = "楼层")
        private String floor;

        @ApiModelProperty(value = "房间名称")
        private String name;

        @ApiModelProperty(value = "房间类型")
        private Integer type;
    }

    @Data
    @ApiModel(value="JZSceneConfigDeviceDataVO", description="房间数据")
    public static class JZSceneConfigDeviceDataVO{
        @ApiModelProperty(value = "楼层")
        private String floor;

        @ApiModelProperty(value = "房间名称")
        private String name;

        @ApiModelProperty(value = "房间类型")
        private Integer type;
    }



}
