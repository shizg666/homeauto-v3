package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.FamilyEnableStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ProjectFamilyTotalVO", description="项目家庭统计列表")
@Builder
public class ProjectFamilyTotalVO {

    @ApiModelProperty(value = "单元数")
    private int unitNum;

    @ApiModelProperty(value = "楼层数")
    private int floorNum;

    @ApiModelProperty(value = "房屋数")
    private int familyNum;

    @ApiModelProperty(value = "户型数")
    private int templateNum;

    @ApiModelProperty(value = "楼栋号")
    private String buildingCode;

}
