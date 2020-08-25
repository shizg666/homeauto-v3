package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.AttributionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 场景详情视图对象
 *
 * @author Yujiumin
 * @version 2020/8/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("场景详情视图对象")
public class SceneDetailVO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备图标")
    private String deviceIcon;

    @ApiModelProperty("设备位置")
    private String devicePosition;

    @ApiModelProperty("设备类别")
    private String deviceCategory;

    @ApiModelProperty("设备属性字符串")
    private String deviceAttrString;

    @ApiModelProperty("设备属性列表")
    private Map<String, String> deviceAttrs;

}
