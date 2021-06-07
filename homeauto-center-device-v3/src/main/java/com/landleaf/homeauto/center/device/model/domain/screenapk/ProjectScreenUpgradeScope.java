package com.landleaf.homeauto.center.device.model.domain.screenapk;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProjectScreenUpgradeScope对象", description="")
public class ProjectScreenUpgradeScope extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推送范围可精确到家庭id")
    private String path;

    @ApiModelProperty(value = "项目升级版本id")
    private Long projectUpgradeId;


}
