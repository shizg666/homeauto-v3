package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 智齿客服平台技能组
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SobotGroup对象", description="智齿客服平台技能组")
public class SobotGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "技能组id")
    private String groupid;

    @ApiModelProperty(value = "技能组名称")
    private String groupName;


}
