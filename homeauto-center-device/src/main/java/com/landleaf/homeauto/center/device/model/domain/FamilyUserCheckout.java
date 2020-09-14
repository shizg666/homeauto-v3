package com.landleaf.homeauto.center.device.model.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yujiumin
 * @version 2020/9/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("family_user_checkout")
@ApiModel(value = "FamilyUserCheckout对象", description = "用户家庭切换表")
public class FamilyUserCheckout extends BaseDO {

    @TableField("family_id")
    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @TableField("user_id")
    @ApiModelProperty(value = "客户id")
    private String userId;


}
