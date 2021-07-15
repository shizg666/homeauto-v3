package com.landleaf.homeauto.center.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 设备功耗统计表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("family_device_energy_day")
@ApiModel(value="FamilyDeviceEnergyDay对象", description="设备功耗统计表")
public class FamilyDeviceEnergyDay extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "基础值")
    private Double basicValue;

    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

    @ApiModelProperty(value = "当天值单位W*ms")
    private Double todayValue;


    @ApiModelProperty(value = "上报时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "设备编码")
    private String deviceSn;

    private Long projectId;

    @ApiModelProperty(value = "楼盘Id")
    private Long realestateId;


}
