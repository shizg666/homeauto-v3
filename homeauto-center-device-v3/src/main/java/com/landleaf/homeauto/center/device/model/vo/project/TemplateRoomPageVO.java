package com.landleaf.homeauto.center.device.model.vo.project;

import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型楼层表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateRoomPageVO", description="户型房间列表展示对象")
public class TemplateRoomPageVO {

    @ApiModelProperty(value = "房间id")
    private String id;

    @ApiModelProperty(value = "房间名称")
    private String name;

    @ApiModelProperty(value = "房间类型")
    private Integer type;

    @ApiModelProperty(value = "房间类型字符串")
    private String typeStr;

    @ApiModelProperty(value = "房间面积")
    private String area;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "设备数量")
    private int count;

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = RoomTypeEnum.getInstByType(type) != null?RoomTypeEnum.getInstByType(type).getName():"";
    }
}
