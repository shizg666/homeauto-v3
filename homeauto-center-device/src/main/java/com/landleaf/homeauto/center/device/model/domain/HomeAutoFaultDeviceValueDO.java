package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
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
 * @author wenyilu
 * @since 2020-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoFaultDeviceValue对象", description="")
@TableName("home_auto_fault_device_value")
public class HomeAutoFaultDeviceValueDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭")
    private String familyId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "所属楼盘")
    private String realestateId;

    @ApiModelProperty(value = "所属项目")
    private String projectId;

    @ApiModelProperty(value = "故障信息")
    private String faultMsg;

    @ApiModelProperty(value = "故障状态")
    private Integer faultStatus;

    @ApiModelProperty(value = "故障时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime faultTime;

    @ApiModelProperty(value = "参考值")
    private String reference;

    @ApiModelProperty(value = "当前值")
    private String current;


}
