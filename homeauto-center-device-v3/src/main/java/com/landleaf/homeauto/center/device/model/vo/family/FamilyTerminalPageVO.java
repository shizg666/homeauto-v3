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
@ApiModel(value="FamilyTerminalPageVO", description="FamilyTerminalPageVO")
public class FamilyTerminalPageVO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "mac")
    private String mac;

    @ApiModelProperty(value = "类型 1大屏2 网关")
    private Integer type;

    @ApiModelProperty(value = "是否是主网关 0否1是")
    private Integer masterFlag;

}
