package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * smarthome楼层房间表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-16
 */
@Data
@ApiModel(value="DeviceAtrributeRangeVO", description="属性范围对象")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtrributeRangeVO {


    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;

    @ApiModelProperty(value = "步幅")
    private String step;




}
