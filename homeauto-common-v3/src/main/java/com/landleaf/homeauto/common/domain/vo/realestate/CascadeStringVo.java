package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value="CascadeStringVo", description="页面级联数据对象")
public class CascadeStringVo<T> implements Serializable {


    private static final long serialVersionUID = 2253054265025964312L;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "显示的值")
    private String label;
    @ApiModelProperty(value = "下级没有就是null")
    private List<T> children;


}
