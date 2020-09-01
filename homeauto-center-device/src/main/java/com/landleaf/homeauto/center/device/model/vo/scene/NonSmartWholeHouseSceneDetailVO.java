package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自由方舟APP全屋场景详情视图对象
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("自由方舟APP全屋场景详情视图对象")
public class NonSmartWholeHouseSceneDetailVO extends BaseSceneVO {

    @ApiModelProperty("是否为常用场景")
    private Integer commonUse;

    @ApiModelProperty("生效区域")
    private String workArea;

    @ApiModelProperty("工作模式")
    private String workMode;

    @ApiModelProperty("温度")
    private String temperature;

    @ApiModelProperty("新风模式")
    private String freshAirMode;
}
