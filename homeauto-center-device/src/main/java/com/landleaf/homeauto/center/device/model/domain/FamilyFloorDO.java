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
 * 家庭楼层表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "FamilyFloor对象", description = "家庭楼层表")
@TableName("family_floor")
public class FamilyFloorDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("floor")
    @ApiModelProperty(value = "楼层")
    private String floor;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭ID")
    private String familyId;


}
