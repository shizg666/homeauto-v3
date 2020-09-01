package com.landleaf.homeauto.center.device.model.domain;

import java.time.LocalDateTime;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭授权记录表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyAuthorization对象", description="家庭授权记录表")
public class FamilyAuthorization extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "授权到期时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "是否执行0否  1是")
    private Integer executeFlag;


}
