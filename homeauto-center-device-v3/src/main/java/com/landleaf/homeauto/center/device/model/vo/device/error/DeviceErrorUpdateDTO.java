package com.landleaf.homeauto.center.device.model.vo.device.error;

import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 属性视图对象
 *
 * @author Yujiumin
 * @version 2020/8/15
 */
@Data
@NoArgsConstructor
@ApiModel(value="DeviceErrorUpdateDTO", description="DeviceErrorUpdateDTO")
public class DeviceErrorUpdateDTO {


    /**
     *  * {@link AttributeErrorTypeEnum}
     */
    @ApiModelProperty("类别 1暖通 2 通信 3数值")
    private Integer type;

    @ApiModelProperty("状态")
    private Integer faultstatus;

    @ApiModelProperty("产品code")
    private List<String> ids;




}
