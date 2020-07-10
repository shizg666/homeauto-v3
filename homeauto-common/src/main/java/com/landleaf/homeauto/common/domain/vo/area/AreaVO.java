package com.landleaf.homeauto.common.domain.vo.area;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value="行政区对象")
public class AreaVO {

    @ApiModelProperty(value = "编码")
    private String value;

    @ApiModelProperty(value = "名称")
    private String label;

}
