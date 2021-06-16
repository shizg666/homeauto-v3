package com.landleaf.homeauto.center.device.model.vo.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="KanBanStatisticsQry", description="KanBanStatisticsQry")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KanBanStatisticsQry {

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;


    @ApiModelProperty(value = "楼栋单元家庭path")
    private List<String> paths;


}
