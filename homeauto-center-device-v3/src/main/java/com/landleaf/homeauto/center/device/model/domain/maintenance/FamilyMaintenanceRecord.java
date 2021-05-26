package com.landleaf.homeauto.center.device.model.domain.maintenance;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
 * 家庭维修记录
 * </p>
 *
 * @author wenyilu
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("family_maintenance_record")
@ApiModel(value="FamilyMaintenanceRecord对象", description="家庭维修记录")
public class FamilyMaintenanceRecord extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭")
    private Long familyId;

    @ApiModelProperty(value = "年月日加6位流水号")
    private Long num;

    @ApiModelProperty(value = "维保时间")
    private LocalDateTime maintenanceTime;

    @ApiModelProperty(value = "维保内容")
    private String content;

    @ApiModelProperty(value = "房屋path")
    private String locatePath;

    @ApiModelProperty(value = "业主姓名")
    private String owner;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty(value = "维保类别(1:手动录入,2:移动端上报)")
    private Integer maintenanceType;



}
