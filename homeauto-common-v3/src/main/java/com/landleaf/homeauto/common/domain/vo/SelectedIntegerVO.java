package com.landleaf.homeauto.common.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "SelectedIntegerVO", description = "页面下拉选择数据对象")
public class SelectedIntegerVO implements Serializable {

    private static final long serialVersionUID = 428564799476819750L;
    @ApiModelProperty(value = "值")
    private Integer value;

    @ApiModelProperty(value = "显示的值")
    private String label;

    public SelectedIntegerVO(String label, Integer value) {
        this.value = value;
        this.label = label;
    }

}
