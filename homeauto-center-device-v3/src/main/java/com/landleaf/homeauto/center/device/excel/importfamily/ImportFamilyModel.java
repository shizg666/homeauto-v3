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
    private String buildingCode;

    @ExcelProperty(index = 3)
    private String unitCode;

    @ExcelProperty(index = 4)
    private String screenMac;

    @ExcelProperty(index = 5)
    private String ip;

    @ExcelProperty(index = 6)
    private String tempalteName;


    private String row;
    private String error;



    @ApiModelProperty(value = "编号")
    private String code;


    @ApiModelProperty(value = "户型主键id")
    private String templateId;


    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "楼盘ID")
    private Long realestateId;

    @ApiModelProperty(value = "起停用状态")
    private Integer enableStatus;

    @ApiModelProperty(value = "权限路径")
    private String path;

    @ApiModelProperty(value = "权限路径名称")
    private String pathName;



}
