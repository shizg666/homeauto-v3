package com.landleaf.homeauto.center.device.model.domain.online;

import java.time.LocalDateTime;
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
 * @since 2021-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyScreenOnline对象", description="")
public class FamilyScreenOnline extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    private String name;

    @ApiModelProperty(value = "状态起始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "状态结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "1:在线，2：离线")
    private Integer status;

    @ApiModelProperty(value = "大屏mac地址")
    private String screenMac;

    @ApiModelProperty(value = "家庭地址")
    private Long familyId;
    @ApiModelProperty(value = "当前记录")
    private Integer current;


}
