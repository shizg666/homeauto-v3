package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自由方舟APP全屋场景详情视图对象
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@ApiModel("自由方舟APP场景详情视图对象")
public class NonSmartSceneDetailVO {

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图片")
    private String picUrl;

    @ApiModelProperty("是否为常用场景")
    private Integer commonUse;

    @ApiModelProperty("生效区域")
    private String workArea;

    @ApiModelProperty("工作模式")
    private String workMode;

    @ApiModelProperty("温度")
    private String temperature;

    @ApiModelProperty("风速")
    private String airSpeed;
}
