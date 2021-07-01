package com.landleaf.homeauto.center.device.model.vo.familymanager;

import com.landleaf.homeauto.center.device.enums.GenderEnum;
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
@ApiModel(value="FamilyManagerPageVO", description="住户管理对象")
public class FamilyManagerPageVO {

    @ApiModelProperty(value = "记录主键id 修改必传")
    private Long id;

    @ApiModelProperty(value = "家庭主键id")
    private Long familyId;

    @ApiModelProperty(value = "住户id")
    private String userId;

    @ApiModelProperty(value = "户号")
    private String doorplate;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "类型str")
    private String typeStr;

    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty(value = "性别")
    private String genderStr;

    public void setGender(Integer gender) {
        this.gender = gender;
        this.genderStr = GenderEnum.getPlatformTypeEnum(gender) == null?"":GenderEnum.getPlatformTypeEnum(gender).getDesc();
    }
}
