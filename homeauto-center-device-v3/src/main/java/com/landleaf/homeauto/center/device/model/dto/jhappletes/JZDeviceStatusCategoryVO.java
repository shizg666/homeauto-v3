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

    @ApiModelProperty(value = "房间列表 默认第一个房间的设备是有值的")
    private List<JZFamilyRoom> rooms;

    @ApiModelProperty(value = "品类")
    private String categoryCode;

    @ApiModelProperty(value = "系统设备信息 品类是面板的时候有值")
    private DeviceInfo systemDevice;


}
