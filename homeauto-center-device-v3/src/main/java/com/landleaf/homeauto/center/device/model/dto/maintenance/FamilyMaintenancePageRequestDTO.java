package com.landleaf.homeauto.center.device.model.dto.maintenance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 家庭维保记录分页请求对象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
@ApiModel("家庭维保记录分页请求对象")
public class FamilyMaintenancePageRequestDTO {

    @ApiModelProperty(value = "房号path")
    private List<String> locatePaths;
    @ApiModelProperty(value = "业主姓名")
    private String owner;
    @ApiModelProperty(value = "时间范围")
    private List<String> times;

    @ApiModelProperty(value = "每页的数量",required = true)
    @Min(value = 1, message = "当前页码不合法")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "当前页",required = true)
    @Min(value = 1, message = "每页展示数量不合法")
    private Integer pageNum = 1;


}
