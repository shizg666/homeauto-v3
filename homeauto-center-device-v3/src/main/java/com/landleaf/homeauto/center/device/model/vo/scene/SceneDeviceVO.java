package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneDeviceVO", description="场景设备信息")
public class SceneDeviceVO {

    @ApiModelProperty("设备id")
    private String id;

    @ApiModelProperty("设备号")
    private String deviceSn;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("楼层")
    private String floor;

    @ApiModelProperty("楼层名称")
    private String floorName;


    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = "产品code")
    private String categoryCode;

    @ApiModelProperty(value = "产品型号")
    private String model;

    @ApiModelProperty(value = "产品品牌")
    private String brandName;

    @ApiModelProperty(value = "产品code")
    private String categoryName;

    @ApiModelProperty(value = "设备属性集合")
    private List<SceneDeviceAttributeVO> attributes;

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = Objects.isNull(CategoryTypeEnum.getInstByType(categoryCode))?"":CategoryTypeEnum.getInstByType(categoryCode).getName();
    }

    public void setFloor(String floor) {
        this.floor = floor;
        this.floorName = floor.concat("楼");
    }
}
