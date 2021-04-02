package com.landleaf.homeauto.center.device.model.smart.bo;

import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceAttributeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 家庭设备业务对象
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel("户式化APP家庭设备业务对象")
public class FamilyDeviceBO {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("设备编码")
    private String deviceCode;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备位置")
    private String devicePosition;

    @ApiModelProperty("设备索引")
    private Integer deviceIndex;

    @ApiModelProperty("产品ID")
    private String productId;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("产品图标")
    private String productIcon;

    @ApiModelProperty("产品图片")
    private String productImage;

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("房间ID")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("房间类型")
    private RoomTypeEnum roomType;

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("楼层号")
    private String floorNum;

    @ApiModelProperty("控制UI")
    private String uiCode;

    @ApiModelProperty("设备属性列表")
    private List<String> deviceAttributeList;
}
