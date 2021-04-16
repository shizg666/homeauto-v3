package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭组表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("family_user")
@ApiModel(value="FamilyUserDO对象", description="家庭组表")
public class FamilyUserDO extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭id")
    private Long familyId;

    @TableField("user_id")
    @ApiModelProperty(value = "客户id")
    private String userId;

    @TableField("type")
    @ApiModelProperty(value = "类型1 管理员账户 3普通用户 2 运维人员账户")
    private Integer type;

    @TableField("last_checked")
    @ApiModelProperty(value = "最后一次选择的家庭 0:false 1:true")
    private Integer lastChecked;


}
