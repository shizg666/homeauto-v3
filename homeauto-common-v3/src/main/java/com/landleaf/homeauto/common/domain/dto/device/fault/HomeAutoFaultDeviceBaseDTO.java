package com.landleaf.homeauto.common.domain.dto.device.fault;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@Accessors(chain = true)
@ApiModel(value="HomeAutoFaultDeviceBaseDTO", description="")
public class HomeAutoFaultDeviceBaseDTO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "家庭")
    private Long familyId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "所属楼盘")
    private Long realestateId;

    @ApiModelProperty(value = "所属项目")
    private Long projectId;

    @ApiModelProperty(value = "故障信息")
    private String faultMsg;

    @ApiModelProperty(value = "故障状态")
    private Integer faultStatus;

    @ApiModelProperty(value = "故障时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime faultTime;


}
