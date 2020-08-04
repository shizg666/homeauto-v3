package com.landleaf.homeauto.common.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "SelectedVo", description = "页面下拉选择数据对象")
@EqualsAndHashCode
public class SelectedVO implements Serializable {


    private static final long serialVersionUID = -195199083136884907L;
    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "显示的值")
    @EqualsAndHashCode.Exclude
    private String label;

    public SelectedVO(String label, String value) {
        this.value = value;
        this.label = label;
    }

}
