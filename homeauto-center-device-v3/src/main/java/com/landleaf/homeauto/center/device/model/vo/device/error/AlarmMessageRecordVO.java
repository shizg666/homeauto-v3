package com.landleaf.homeauto.center.device.model.vo.device.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("app安防报警记录")
public class AlarmMessageRecordVO {

    @ApiModelProperty("报警日期")
    private String date;

    @ApiModelProperty("报警详情")
    private List<AlarmMessageRecordItemVO> details;
}
