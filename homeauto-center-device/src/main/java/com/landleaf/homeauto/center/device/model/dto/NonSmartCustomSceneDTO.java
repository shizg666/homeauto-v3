package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自由方舟APP自定义场景数据传输对象
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@ApiModel("自由方舟APP自定义场景数据传输对象")
public class NonSmartCustomSceneDTO {

    @ApiModelProperty("场景ID")
    private String sceneId;

    @ApiModelProperty(value = "家庭ID", required = true)
    private String familyId;

    @ApiModelProperty(value = "场景名称", required = true)
    private String sceneName;

    @ApiModelProperty(value = "场景图片", required = true)
    private String picUrl;

    @ApiModelProperty(value = "是否为常用场景", required = true)
    private Integer commonUse;

    @ApiModelProperty(value = "生效房间ID", required = true)
    private List<String> rooms;

    @ApiModelProperty(value = "工作模式", required = true)
    private Integer workMode;

    @ApiModelProperty(value = "温度", required = true)
    private Integer temperature;

    @ApiModelProperty(value = "风速", required = true)
    private Integer airSpeed;

}
