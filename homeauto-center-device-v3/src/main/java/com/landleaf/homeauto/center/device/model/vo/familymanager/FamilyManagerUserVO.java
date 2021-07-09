package com.landleaf.homeauto.center.device.model.vo.familymanager;

import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
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
@ApiModel(value="FamilyManagerUserVO", description="FamilyManagerUserVO")
public class FamilyManagerUserVO {

    @ApiModelProperty("对于家庭id")
    private Long familyId;

    @ApiModelProperty(value = "住户类型 1管理员 3 普通普通成员")
    private Integer type;

    @ApiModelProperty(value = "住户类型 1管理员 3 普通普通成员")
    private String typeStr;

    @ApiModelProperty(value = "户号")
    private String doorplate;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = FamilyUserTypeEnum.getInstByType(type).getName();
    }
}
