package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型情景表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyScene", description="家庭情景表")
public class FamilyScene extends BaseEntity2 {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "情景名称")
    private String name;

    @ApiModelProperty(value = "家庭id")
    private Long familyId;

    @ApiModelProperty(value = "场景类型1 全屋场景 2 智能场景")
    private Integer type;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "是否可修改 1是 0否 ")
    private Integer updateFlag;

    @ApiModelProperty(value = "场景图标")
    private String icon;

//    @ApiModelProperty(value = "大屏是否可修改 1是 0否 ")
//    private Integer updateFlagScreen;

    @ApiModelProperty(value = "场景no")
    private String sceneNo;


}
