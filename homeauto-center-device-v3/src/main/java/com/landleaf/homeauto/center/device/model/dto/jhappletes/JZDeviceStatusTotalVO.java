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
@ApiModel(value="JZDeviceStatusTotalVO", description="设备运行统计")
public class JZDeviceStatusTotalVO {

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "品类")
    private String categoryCode;

    @ApiModelProperty(value = "正在运行的设备数量")
    private int switchOnNum;

}
