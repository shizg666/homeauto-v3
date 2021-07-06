package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.SceneAttributeInfoBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="FamilySceneDeviceActionBO", description="查看-场景设备动作详情信息")
public class FamilySceneDeviceActionBO {

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;


    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "属性配置")
    private List<SceneAttributeInfoBO> actions;

}
