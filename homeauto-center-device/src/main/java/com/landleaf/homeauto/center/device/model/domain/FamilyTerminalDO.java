package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyTerminal对象", description="家庭终端表")
public class FamilyTerminalDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型 1大屏2 网关")
    private Integer type;

    @ApiModelProperty(value = "是否是主网关 ")
    private Integer masterFlag;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;


}
