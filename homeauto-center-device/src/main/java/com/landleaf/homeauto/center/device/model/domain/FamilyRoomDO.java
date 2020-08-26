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
 * 家庭房间表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "FamilyRoom对象", description = "家庭房间表")
@TableName("family_room")
public class FamilyRoomDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    @TableField("sort_no")
    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @TableField("floor_id")
    @ApiModelProperty(value = "楼层ID")
    private String floorId;

    @TableField("type")
    @ApiModelProperty(value = "类型")
    private Integer type;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭ID")
    private String familyId;


}
