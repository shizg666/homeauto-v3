package com.landleaf.homeauto.common.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "SelectedVo", description = "页面下拉选择数据对象")
@EqualsAndHashCode
public class SelectedVO implements Serializable {


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
