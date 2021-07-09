package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("app安防报警记录详情")
public class JZAlarmMessageVO {

    @ApiModelProperty("防区报警设备")
    private String zoneDevice;

    @ApiModelProperty("报警信息")
    private String context;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @ApiModelProperty("报警时间")
    private LocalDateTime ts;

}
