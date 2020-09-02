package com.landleaf.homeauto.common.domain.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value="级联数据对象", description="页面级联数据对象")
@Builder
public class CascadeVo implements Serializable {


    private static final long serialVersionUID = 2253054265025964312L;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "显示的值")
    private String label;
    @ApiModelProperty(value = "下级没有就是null")
    private List<CascadeVo> children;

    public CascadeVo(String label, String value) {
        this.value = value;
        this.label = label;
    }

    public CascadeVo(String label, String value, List<CascadeVo> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

}
