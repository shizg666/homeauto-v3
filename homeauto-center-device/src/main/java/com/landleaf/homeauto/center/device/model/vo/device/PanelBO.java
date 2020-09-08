package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭设备
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="PanelBO", description="面板下拉对象")
public class PanelBO {

    @ApiModelProperty("设备号")
    private String sn;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("设备图标")
    private String deviceIcon;

    @ApiModelProperty("设备开关状态")
    private Integer deviceSwitch;

    @ApiModelProperty("索引值")
    private Integer index;

}
