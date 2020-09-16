package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工程批量导入配置对象
 */
@Data
public class ImportFamilyModel {

    private String id;

    @ExcelProperty(index = 0)
    private String name;

     @ExcelProperty(index = 1)
    private String roomNo;

    @ExcelProperty(index = 2)
    private String mac1;

    @ExcelProperty(index = 3)
    private String mac2;

    @ExcelProperty(index = 4)
    private String mac3;

    @ExcelProperty(index = 5)
    private String mac4;


    private String row;
    private String error;



    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "审核状态0 未审核，1 已审核,2 授权中")
    private Integer reviewStatus;

    @ApiModelProperty(value = "交付状态0 未交付，1 已交付 2 已激活")
    private Integer deliveryStatus;

    @TableField("template_name")
    @ApiModelProperty(value = "户型")
    private String templateName;

    @ApiModelProperty(value = "单元id")
    private String unitId;

    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    @ApiModelProperty(value = "权限路径")
    private String path;

    @ApiModelProperty(value = "权限路径名称")
    private String pathName;

    @ApiModelProperty(value = "面积")
    private String area;


}
