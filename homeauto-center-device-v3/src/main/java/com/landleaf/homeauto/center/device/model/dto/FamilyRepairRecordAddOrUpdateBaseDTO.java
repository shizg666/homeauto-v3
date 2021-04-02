package com.landleaf.homeauto.center.device.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 家庭维修记录 新增请求对象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
public class FamilyRepairRecordAddOrUpdateBaseDTO {

    @ApiModelProperty(value = "维修单名称")
    private String name;

    @ApiModelProperty(value = "楼盘Id")
    private String realestateId;

    @ApiModelProperty(value = "项目ID")
    private String projectId;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @ApiModelProperty(value = "维修单编号")
    private String repairNo;

    @ApiModelProperty(value = "调试人员")
    private String testMan;

    @ApiModelProperty(value = "维修开始日期")
    private String startTime;

    @ApiModelProperty(value = "结束日期精确到日")
    private String endTime;

    @ApiModelProperty(value = "详细说明")
    private String remark;

    @ApiModelProperty(value = "楼栋")
    private String buildingCode;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @TableField("unit_code")
    @ApiModelProperty(value = "单元")
    private String unitCode;

    @ApiModelProperty(value = "号")
    private String roomNo;

    @ApiModelProperty(value = "问题原因")
    private String problem;

    @ApiModelProperty(value = "客户信息")
    private String customerInfo;

    @ApiModelProperty(value = "附件地址")
    private List<FamilyRepairRecordEnclosureDTO> enclosures;


}
