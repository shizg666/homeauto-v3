package com.landleaf.homeauto.center.device.model.domain.status;

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
 * 设备状态表
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyDeviceStatusHistoryDTO对象", description="设备状态表excel导出")
public class FamilyDeviceStatusHistoryDTO  {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

    @ApiModelProperty(value = "家庭名称")
    private String familyName;

    @ApiModelProperty(value = "状态码")
    private String statusCode;

    @ApiModelProperty(value = "状态值")
    private String statusValue;

    @ApiModelProperty(value = "单位")
    private String unitType;

    @ApiModelProperty("门牌号")
    private String familyNumber;

    @ApiModelProperty("户型名称")
    private String templateName;

    @ApiModelProperty("单元号")
    private String unitCode;

    @ApiModelProperty("楼栋号")
    private String buildingCode;
    @ApiModelProperty(value = "产品码")
    private String productCode;

    @ApiModelProperty(value = "品类码")
    private String categoryCode;

    @ApiModelProperty(value = "上报时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "设备编码")
    private String deviceSn;


    @ApiModelProperty(value = "属性状态类型(枚举、数值见产品属性定义)")
    private Integer statusType;


}
