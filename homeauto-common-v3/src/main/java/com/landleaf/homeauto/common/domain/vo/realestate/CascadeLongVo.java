package com.landleaf.homeauto.common.domain.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value="CascadeLongVo", description="页面级联数据对象")
public class CascadeLongVo implements Serializable {


    private static final long serialVersionUID = 2253054265025964312L;
    @ApiModelProperty(value = "值")
    private Long value;
    @ApiModelProperty(value = "显示的值")
    private String label;
    @ApiModelProperty(value = "下级没有就是null")
    private List<CascadeLongVo> children;

    public CascadeLongVo(String label, Long value) {
        this.value = value;
        this.label = label;
    }

    public CascadeLongVo(String label, Long value, List<CascadeLongVo> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

}
