package com.landleaf.homeauto.center.device.model.domain;

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
 * 终端在线离线状态记录表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyTerminalOnlineStatusDO对象", description="终端在线离线状态记录表")
public class FamilyTerminalOnlineStatusDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "终端id")
    private String terminalId;

    @ApiModelProperty(value = "家庭id")
    private String familyId;

    @ApiModelProperty(value = "状态（1：在线，2：离线）")
    private Integer status;

    @ApiModelProperty(value = "终端mac地址")
    private String mac;

    @ApiModelProperty(value = "状态开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "状态结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime endTime;


}
