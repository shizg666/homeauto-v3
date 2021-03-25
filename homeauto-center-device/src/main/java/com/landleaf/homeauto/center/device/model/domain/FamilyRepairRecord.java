package com.landleaf.homeauto.center.device.model.domain;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.BaseEntity;
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
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyRepairRecord对象", description="家庭维修记录")
public class FamilyRepairRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "维修单名称")
    private String name;

    @ApiModelProperty(value = "楼盘Id")
    private String realestateId;

    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;

    @ApiModelProperty(value = "项目ID")
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @ApiModelProperty(value = "家庭名称")
    private String familyName;

    @ApiModelProperty(value = "维修单编号")
    private String repairNo;

    @ApiModelProperty(value = "调试人员")
    private String testMan;

    @ApiModelProperty(value = "维修开始日期")
    private LocalDate startTime;

    @ApiModelProperty(value = "详细说明")
    private String remark;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "户型名称")
    private String templateName;
    @ApiModelProperty(value = "楼栋")
    private String buildingCode;
    @TableField("unit_code")
    @ApiModelProperty(value = "单元")
    private String unitCode;
    @ApiModelProperty(value = "号")
    private String roomNo;
    @ApiModelProperty(value = "问题原因")
    private String problem;
    @ApiModelProperty(value = "结束日期精确到日")
    private LocalDate endTime;
    @ApiModelProperty(value = "客户信息")
    private String customerInfo;


}
