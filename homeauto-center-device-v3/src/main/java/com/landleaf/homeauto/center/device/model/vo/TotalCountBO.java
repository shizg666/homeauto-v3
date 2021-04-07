package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName TotalCountBO
 * @Description: TODO
 * @Author shizg
 * @Date 2021/4/6
 * @Version V1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="TotalCountBO", description="统计对象")
@EqualsAndHashCode
public class TotalCountBO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "数量")
    private int count;
}
