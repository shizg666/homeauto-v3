package com.landleaf.homeauto.center.device.model.vo.familymanager;

import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserPageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyManageDetailVO", description="住户管理详情对象")
public class FamilyManageDetailVO {


    /**
     * {@link com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum}
     */
    @ApiModelProperty(value = "住户类型 1管理员 3 普通普通成员")
    private Integer userType;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private Integer gender;
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

    @ApiModelProperty(value = "绑定家庭信息列表")
    private List<FamilyManagerUserVO> familys;

    @ApiModelProperty(value = "成员信息")
    private List<FamilyUserPageVO> members;


}
