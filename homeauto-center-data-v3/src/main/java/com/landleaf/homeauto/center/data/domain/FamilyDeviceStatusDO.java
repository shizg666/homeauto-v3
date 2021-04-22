package com.landleaf.homeauto.center.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class FamilyDeviceStatusDO extends BaseDO {

    private static final long serialVersionUID = 1L;


    @TableField("status_code")
    @ApiModelProperty(value = "状态码")
    private String statusCode;
    @TableField("device_sn")
    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @TableField("status_value")
    @ApiModelProperty(value = "状态值")
    private String statusValue;

    @TableField("family_id")
    @ApiModelProperty(value = "家庭ID")
    private Long familyId;

    @TableField("project_id")
    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @TableField("product_code")
    @ApiModelProperty(value = "产品码")
    private String productCode;

    @TableField("category_code")
    @ApiModelProperty(value = "品类码")
    private String categoryCode;

    @TableField("begin_time")
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @TableField("end_time")
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;


}
