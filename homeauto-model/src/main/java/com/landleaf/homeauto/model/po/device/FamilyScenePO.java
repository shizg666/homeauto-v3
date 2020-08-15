package com.landleaf.homeauto.model.po.device;

import com.landleaf.homeauto.model.po.base.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭情景表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyScene对象", description="家庭情景表")
public class FamilyScenePO extends BasePO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "情景名称")
    private String name;

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "场景类型1 全屋场景 2 智能场景")
    private Integer type;

    @ApiModelProperty(value = "0 非默认 1 是默认")
    private Integer defaultFlag;

    @ApiModelProperty(value = "app是否可修改 1是 0否 ")
    private Integer updateFlagApp;

    @ApiModelProperty(value = "场景图标")
    private String icon;

    @ApiModelProperty(value = "大屏是否可修改 1是 0否 ")
    private Integer defaultFlagScreen;


}
