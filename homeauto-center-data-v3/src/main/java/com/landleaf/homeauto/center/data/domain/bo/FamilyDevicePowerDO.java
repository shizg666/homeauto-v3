package com.landleaf.homeauto.center.data.domain.bo;

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
 * 设备功率
 * </p>
 *
 */
@Data
@ApiModel(value="FamilyDevicePowerDO对象", description="设备功率表")
public class FamilyDevicePowerDO  {


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


}
