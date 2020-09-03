package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="FamilyTerminalOperateVO", description="FamilyTerminalOperateVO")
public class FamilyTerminalOperateVO {

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @ApiModelProperty(value = "主键id(修改必传)")
    private String id;




}
