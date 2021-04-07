package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型房间表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("house_template_room")
@ApiModel(value="TemplateRoom对象", description="户型房间表")
public class TemplateRoomDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;

    @TableField("img_icon")
    @ApiModelProperty(value = "房间图标icon")
    private String imgIcon;

    @ApiModelProperty(value = "房间面积")
    private String area;

    @ApiModelProperty(value = "小程序图片")
    private String imgApplets;

    @ApiModelProperty(value = "图片扩展预留")
    private String imgExpand;

}
