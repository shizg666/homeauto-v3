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
@ToString(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel(value="MaintenanceStatistics", description="看板-维保")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MaintenanceStatistics extends KanBanStatistics{

    @ApiModelProperty(value = "比上个月的增量")
    private Integer increment;


}
