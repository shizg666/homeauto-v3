package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="JZDeviceStatusCategoryVO", description="品类设备状态")
public class JZDeviceStatusCategoryVO {

    @ApiModelProperty(value = "房间列表")
    private List<RoomInfo> rooms;

    @ApiModelProperty(value = "品类")
    private String categoryCode;

    @ApiModelProperty(value = "系统设备信息 品类是面板的时候有值")
    private JZDeviceAttrDataVO systemDevice;

    @Data
    @ApiModel(value="RoomInfo", description="房间信息")
    private static class RoomInfo{
        @ApiModelProperty(value = "房间名称")
        private String name;

        @ApiModelProperty(value = "房间类型")
        private Integer type;
    }

    @Data
    @ApiModel(value="DeviceInfo", description="设备信息")
    private static class DeviceInfo{
        @ApiModelProperty(value = "设备id")
        private Long deviceId;

        @ApiModelProperty(value = "设备名称")
        private String deviceName;

        @ApiModelProperty(value = "设备属性")
        private List<JZDeviceAttrDataVO> attrs;
    }

    @Data
    @ApiModel(value="JZDeviceAttrDataVO", description="设备属性信息")
    public static  class JZDeviceAttrDataVO {

        @ApiModelProperty("属性id")
        private Long attrId;

        @ApiModelProperty("属性code")
        private String code;

        @ApiModelProperty("属性名称")
        private String name;

        @ApiModelProperty("当前值")
        private String currentVal;

        /**
         * {@link com.landleaf.homeauto.common.enums.category.AttributeTypeEnum}
         */
        @ApiModelProperty("属性类型 1 多选 2值域")
        private Integer type;

        @ApiModelProperty("属性范围对象 type=1 值域有值")
        private AttributeScopeVO scopeVO;

        @ApiModelProperty(value = "属性可选值 type=2 值域有值")
        private List<SceneDeviceAttributeInfoVO> infos;
    }

}
