package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyUserInfoVO", description="FamilyUserInfoVO")
public class FamilyUserInfoVO {

    @ApiModelProperty("成员id主键")
    private String memberId;

    @ApiModelProperty("成员名称")
    private String name;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("是否是管理员 0否 1是")
    private Integer adminFlag;

    private String userId;

}
