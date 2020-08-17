package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.center.device.model.po.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭常用设备表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "FamilyCommonDevice对象", description = "家庭常用设备表")
public class FamilyCommonDeviceDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer sortNo;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

}
