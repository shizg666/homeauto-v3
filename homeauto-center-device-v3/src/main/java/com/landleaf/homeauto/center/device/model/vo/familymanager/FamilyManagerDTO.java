package com.landleaf.homeauto.center.device.model.vo.familymanager;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="FamilyManagerDTO", description="住户管理对象")
public class FamilyManagerDTO {

    @ApiModelProperty(value = "记录主键id 修改必传")
    private Long id;

    @ApiModelProperty(value = "家庭主键id")
    private Long familyId;

    /**
     * {@link com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum}
     */
    @ApiModelProperty(value = "住户类型 1管理员 3 普通普通成员")
    private Integer type;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private Integer sex;
    @ApiModelProperty(value = "证件类型 1:身份证 2：军官证")
    private Integer certType;
    @ApiModelProperty(value = "证件")
    private String cert;
    @ApiModelProperty(value = "户籍路径code")
    private String censusPath;
    @ApiModelProperty(value = "户籍")
    private String censusName;

    @ApiModelProperty(value = "绑定时间")
    private String bindTime;

    @ApiModelProperty(value = "有效期")
    private String validTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
