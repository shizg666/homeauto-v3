package com.landleaf.homeauto.common.domain.dto.device.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: app故障报修详情DTO
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@ApiModel("app故障报修详情DTO")
@Data
public class AppRepairDetailLogDTO {

    @ApiModelProperty(value = "状态")
    private String statusName;

    @ApiModelProperty(value = "操作日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime operateTime;


}
