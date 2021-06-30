package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilySceneActionConfig", description="家庭场景动作配置")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilySceneActionConfig extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    @ApiModelProperty(value = "属性值")
    private String attributeVal;

    @ApiModelProperty(value = "属性code")
    private String attributeCode;

    @ApiModelProperty(value = "设别号")
    private String deviceSn;

    @ApiModelProperty(value = "场景id")
    private Long sceneId;

    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "户型id")
    private Long templateId;

    @ApiModelProperty(value = "家庭id")
    private Long familyId;


}
