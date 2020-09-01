package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@ApiModel("场景基础视图对象")
public class BaseSceneVO {

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图片")
    private String picUrl;

}
