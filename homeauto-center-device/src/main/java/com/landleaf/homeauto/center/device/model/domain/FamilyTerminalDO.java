package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("family_terminal")
public class FamilyTerminalDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("mac")
    @ApiModelProperty(value = "mac地址")
    private String mac;

    @TableField("type")
    @ApiModelProperty(value = "类型 1大屏2 网关")
    private Integer type;

    @TableField("master_flag")
    @ApiModelProperty(value = "是否是主网关 ")
    private Integer masterFlag;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭ID")
    private String familyId;


}
