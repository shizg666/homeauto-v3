package com.landleaf.homeauto.center.device.model.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="设备值属性信息")
public class BasicAttrInfoDTO {

    @ApiModelProperty(value = "属性名称")
    private String  name;

    @ApiModelProperty(value = "属性code")
    private String  code;


    @ApiModelProperty(value = "属性value")
    private String  value;


    @ApiModelProperty(value = "属性value中文")
    private String  valueStr;
    @ApiModelProperty(value = "故障标记")
    private Integer FaultFlag;

    @ApiModelProperty(value = "在线离线标记")
    private Integer onlineFlag;
}
