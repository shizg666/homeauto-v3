package com.landleaf.homeauto.center.device.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/10/14
 */
@Data
@NoArgsConstructor
@ApiModel("家庭设备视图层对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDeviceVO {

    @ApiModelProperty("家庭id")
    private String familyId;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("楼层id")
    private String floorId;

    @ApiModelProperty("楼层号")
    private String floorNum;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("产品Code")
    private String productCode;

    @ApiModelProperty("品类Code")
    private String categoryCode;

    @ApiModelProperty("设备图标")
    private String deviceIcon;

    @ApiModelProperty("设备开关状态")
    private Integer deviceSwitch;

    @ApiModelProperty("索引值")
    private Integer index;

}
