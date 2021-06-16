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
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DeviceStatistics", description="看板-设备")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStatistics extends KanBanStatistics{



    @ApiModelProperty(value = "离线数量")
    private Integer offlineCount;

    @ApiModelProperty(value = "离线数量")
    private Integer errorCount;


}
