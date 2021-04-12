package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="ProjectFamilyTotalBO", description="ProjectFamilyTotalBO")
public class ProjectFamilyTotalBO {

    @ApiModelProperty(value = "单元")
    private String unitCode;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "房间id")
    private String familyId;

    @ApiModelProperty(value = "户型id")
    private String templateId;

    @ApiModelProperty(value = "楼栋号")
    private String buildingCode;

}
