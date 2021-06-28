package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.common.domain.dto.AppDeviceAttributeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/31
 */
@Data
@NoArgsConstructor
@ApiModel("设备控制数据传输对象")
public class JzDeviceCommandDTO {
    @NotNull(message = "楼栋号不能为空")
    @ApiModelProperty(value = "楼栋")
    private String buildCode;

    @NotNull(message = "单元不能为空")
    @ApiModelProperty(value = "单元")
    private String unitCode;

    @NotNull(message = "门牌号不能为空")
    @ApiModelProperty(value = "门牌号")
    private String doorplate;

    @ApiModelProperty(value = "设备ID",required = true)
    private Long deviceId;

    @ApiModelProperty("控制参数")
    private JZAppDeviceAttributeDTO data;

}
