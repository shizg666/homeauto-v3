package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 家庭终端表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyTerminalVO", description="家庭终端")
public class FamilyTerminalVO {

    @ApiModelProperty(value = "主键id(修改必传)")
    private String id;

    @NotEmpty(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id(必传)")
    private String projectId;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotEmpty(message = "mac不能为空")
    @ApiModelProperty(value = "mac")
    private String mac;


    @ApiModelProperty(value = "类型 1大屏2 网关")
    private Integer type;

    @ApiModelProperty(value = "是否是主网关 0否1是")
    private Integer masterFlag;




}
