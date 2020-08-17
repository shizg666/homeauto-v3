package com.landleaf.homeauto.model.po.device;

import com.landleaf.homeauto.model.po.base.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭常用场景表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyCommonScene对象", description="家庭常用场景表")
public class FamilyCommonScenePO extends BasePO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "设备ID")
    private String sceneId;


}
