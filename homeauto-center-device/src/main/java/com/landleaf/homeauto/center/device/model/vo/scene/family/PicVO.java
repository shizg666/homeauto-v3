package com.landleaf.homeauto.center.device.model.vo.scene.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="PicVO", description="图片")
public class PicVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty(value = "模式")
    private String mode;

    @ApiModelProperty(value = "url")
    private String url;




}
