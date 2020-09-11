package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 场景关联非暖通设备动作表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HouseTemplateSceneAction对象", description="场景关联非暖通设备动作表")
public class HouseTemplateSceneAction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "属性值")
    private String val;

    @ApiModelProperty(value = "比较和状态: 0-等于 1-大于 2大于等于 -1-小于 -2-小于等于")
    private Integer operator;

    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;

    @ApiModelProperty(value = "属性code")
    private String attributeCode;


}
