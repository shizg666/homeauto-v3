package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 家庭维修记录 查詢請求對象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
@ApiModel("家庭维修记录 查詢請求對象")
public class FamilyRepairRecordPageRequestDTO{

    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "家庭名称")
    private String familyName;
    @ApiModelProperty(value = "维修单名称")
    private String name;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "问题原因")
    private String problem;

    @ApiModelProperty(value = "时间范围")
    private List<String> times;

    @ApiModelProperty(value = "每页的数量",required = true)
    @Min(value = 1, message = "当前页码不合法")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "当前页",required = true)
    @Min(value = 1, message = "每页展示数量不合法")
    private Integer pageNum = 1;


}
