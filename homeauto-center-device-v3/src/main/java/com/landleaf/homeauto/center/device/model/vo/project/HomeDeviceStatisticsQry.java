package com.landleaf.homeauto.center.device.model.vo.project;

import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
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
@ApiModel(value="HomeDeviceStatisticsQry", description="HomeDeviceStatisticsQry")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeDeviceStatisticsQry {

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;


    @ApiModelProperty(value = "项目集合")
    private String projectIds;


}
