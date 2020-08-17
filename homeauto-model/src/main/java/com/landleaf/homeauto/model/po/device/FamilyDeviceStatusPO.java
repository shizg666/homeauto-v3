package com.landleaf.homeauto.model.po.device;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.model.po.base.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("family_device_status")
@ApiModel(value="FamilyDeviceStatus对象", description="")
public class FamilyDeviceStatusPO extends BasePO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "设备序列号")
    private String deviceSn;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "状态值")
    private String statusValue;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @ApiModelProperty(value = "产品码")
    private String productCode;

    @ApiModelProperty(value = "品类码")
    private String categoryCode;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;


}
