package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
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
@ApiModel(value="FamilyRoomDeviceAttrBO", description="家庭房间设备属性信息")
public class FamilyRoomDeviceAttrBO {

    @ApiModelProperty("设备id")
    private Long deviceId;

    @ApiModelProperty("设备号")
    private String deviceSn;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("房间类型")
    private Integer roomType;

    @ApiModelProperty("楼层")
    private String floor;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = Objects.isNull(CategoryTypeEnum.getInstByType(categoryCode))?"":CategoryTypeEnum.getInstByType(categoryCode).getName();
    }
}
