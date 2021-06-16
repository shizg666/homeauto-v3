package com.landleaf.homeauto.center.device.model.vo.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@ApiModel(value="DeviceErrorStatistics", description="看板-设备故障信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceErrorStatistics extends KanBanStatistics{


    @ApiModelProperty(value = "影响户数")
    private Integer familyCount;


}
