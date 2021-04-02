package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
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
 * @since 2020-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_glc_wind_status")
@ApiModel(value="HomeAutoGlcWindStatus对象", description="")
public class HomeAutoGlcWindStatusDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭")
    private String familyId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "大屏上报值")
    private String value;


}
