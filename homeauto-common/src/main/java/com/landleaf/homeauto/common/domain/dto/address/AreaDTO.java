package com.landleaf.homeauto.common.domain.dto.address;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value="AreaDTO", description="行政区基本信息")
public class AreaDTO {

    @ApiModelProperty(value = "编码")
    private String value;

    @ApiModelProperty(value = "名称")
    private String label;

}
