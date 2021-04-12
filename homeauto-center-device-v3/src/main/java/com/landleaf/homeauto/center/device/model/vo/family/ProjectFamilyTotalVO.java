package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.FamilyEnableStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="ProjectFamilyTotalVO", description="项目家庭统计列表")
public class ProjectFamilyTotalVO {

    @ApiModelProperty(value = "单元数")
    private String unitNum;

    @ApiModelProperty(value = "楼层数")
    private String floorNum;

    @ApiModelProperty(value = "房屋数")
    private String familyNum;

    @ApiModelProperty(value = "户型数")
    private String templateNum;

    @ApiModelProperty(value = "楼栋号")
    private String buildingCode;

}
