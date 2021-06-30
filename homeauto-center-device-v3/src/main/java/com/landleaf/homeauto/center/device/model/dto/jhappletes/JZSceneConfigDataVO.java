package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeInfoVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
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
@ApiModel(value="JZSceneConfigDataVO", description="场景配置数据获取")
public class JZSceneConfigDataVO {

    @ApiModelProperty(value = "房间数据")
    List<JZSceneConfigRoomDataVO> rooms;

    @ApiModelProperty(value = "暖通设备")
    JZSceneConfigDeviceDataVO systemDevice;

    @Data
    @ApiModel(value="JZSceneConfigRoomDataVO", description="房间数据")
    public static class JZSceneConfigRoomDataVO{
        @ApiModelProperty(value = "楼层")
        private String floor;

        @ApiModelProperty(value = "房间名称")
        private String name;

        @ApiModelProperty(value = "房间类型")
        private Integer type;

        @ApiModelProperty(value = "设备列表")
        private List<JZSceneConfigDeviceDataVO> devices;
    }

    @Data
    @ApiModel(value="JZSceneConfigDeviceDataVO", description="设备数据")
    public static class JZSceneConfigDeviceDataVO{
        @ApiModelProperty("设备id")
        private String deviceId;

        @ApiModelProperty("设备号")
        private String deviceSn;

        @ApiModelProperty("设备名称")
        private String deviceName;

        @ApiModelProperty(value = "产品id")
        private Long productId;

        @ApiModelProperty(value = "产品code")
        private String productCode;

        @ApiModelProperty(value = "品类code")
        private String categoryCode;

        @ApiModelProperty(value = "品类名称")
        private String categoryName;

        @ApiModelProperty(value = "属性集合")
        private List<JZSceneConfigDeviceAttrDataVO> attributes;
    }

    @Data
    @ApiModel(value="JZSceneConfigDeviceAttrDataVO", description="场景设备属性信息")
    public static  class JZSceneConfigDeviceAttrDataVO {

        @ApiModelProperty("属性id")
        private Long attrId;

        @ApiModelProperty("属性code")
        private String code;

        @ApiModelProperty("属性名称")
        private String name;

        /**
         * {@link com.landleaf.homeauto.common.enums.category.AttributeTypeEnum}
         */
        @ApiModelProperty("属性类型 1 多选 2值域")
        private Integer type;

        @ApiModelProperty("属性范围对象 type=1 值域有值")
        private AttributeScopeVO scopeVO;

        @ApiModelProperty(value = "属性可选值 type=2 枚举有值")
        private List<SceneDeviceAttributeInfoVO> infos;



    }



}
