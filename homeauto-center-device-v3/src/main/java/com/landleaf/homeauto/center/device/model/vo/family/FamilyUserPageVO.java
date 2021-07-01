package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyUserPageVO", description="用户家庭成员列表对象")
public class FamilyUserPageVO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "客户id")
    private String userId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "客户姓名")
    private String name;

    @ApiModelProperty(value = "类型1 管理员账户 0普通用户 2 运维人员账户")
    private Integer type;

    @ApiModelProperty(value = "类型1 管理员账户 0普通用户 2 运维人员账户")
    private String typeStr;

    @ApiModelProperty(value = "门牌号")
    private String doorplate;
    @ApiModelProperty(value = "楼栋名称")
    private String buildingName;
    @ApiModelProperty(value = "单元名称")
    private String unitName;

    @ApiModelProperty(value = "家庭名称")
    private String FamilyName;

    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private Integer gender;

    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private String genderStr;

    public void setGender(Integer gender) {
        this.gender = gender;
        this.genderStr = GenderEnum.getPlatformTypeEnum(gender) == null?"":GenderEnum.getPlatformTypeEnum(gender).getDesc();
    }

    @ApiModelProperty(value = "绑定时间")
    private String bindTime;

    @ApiModelProperty(value = "有效期")
    private String validTime;

    @ApiModelProperty(value = "家庭主键id")
    private Long familyId;

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = Objects.nonNull(FamilyUserTypeEnum.getInstByType(type))?FamilyUserTypeEnum.getInstByType(type).getName():"";
    }

}
