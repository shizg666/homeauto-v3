package com.landleaf.homeauto.common.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "SelectedLongVO", description = "页面下拉选择数据对象")
@EqualsAndHashCode
public class SelectedLongVO implements Serializable {


    private static final long serialVersionUID = -195199083136884907L;
    @ApiModelProperty(value = "值")
    private Long value;

    @ApiModelProperty(value = "显示的值")
    @EqualsAndHashCode.Exclude
    private String label;

    public SelectedLongVO(String label, Long value) {
        this.value = value;
        this.label = label;
    }

}
