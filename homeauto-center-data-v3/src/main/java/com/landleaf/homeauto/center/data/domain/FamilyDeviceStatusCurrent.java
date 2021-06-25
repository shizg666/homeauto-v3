package com.landleaf.homeauto.center.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备状态表
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("family_device_status_current")
@ApiModel(value="FamilyDeviceStatusCurrent对象", description="设备状态表")
public class FamilyDeviceStatusCurrent extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "状态值")
    private String statusValue;

    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

    @ApiModelProperty(value = "产品码")
    private String productCode;

    @ApiModelProperty(value = "品类码")
    private String categoryCode;

    @ApiModelProperty(value = "上报时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "设备编码")
    private String deviceSn;

    private Long projectId;

    @ApiModelProperty(value = "楼盘Id")
    private Long realestateId;

//    @ApiModelProperty(value = "属性状态类型(枚举、数值见产品属性定义)")
//    private Integer statusType;


}
