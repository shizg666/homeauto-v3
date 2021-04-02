package com.landleaf.homeauto.common.domain.vo;

import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
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
@ApiModel(value="级联数据对象2", description="页面级联数据对象")
public class CascadeIntegerVo implements Serializable {


    private static final long serialVersionUID = -7846301592036805565L;
    @ApiModelProperty(value = "值")
    private Integer value;
    @ApiModelProperty(value = "显示的值")
    private String label;
    @ApiModelProperty(value = "下级没有就是null")
    private List<CascadeVo> children;

    public CascadeIntegerVo(String label, Integer value) {
        this.value = value;
        this.label = label;
    }

    public CascadeIntegerVo(String label, Integer value, List<CascadeVo> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

}
