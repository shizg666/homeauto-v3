package com.landleaf.homeauto.contact.screen.client.dto.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 场景关联设备动作表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
public class ContactScreenSceneAction {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品属性id")
    private String attributeCode;

    @ApiModelProperty(value = "属性值")
    private String attributeVal;

    @ApiModelProperty(value = "比较和状态: 0-等于 1-大于 2大于等于 -1-小于 -2-小于等于")
    private Integer operator;

    @ApiModelProperty(value = "类型 0是非暖通的配置 1是暖通相关的配置")
    private Integer type;


}
