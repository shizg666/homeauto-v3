package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * 本地数采导出配置对象
 */
@Data
@ContentRowHeight(12)
@HeadRowHeight(20)
@ColumnWidth(15)
public class LocalDataCollectExportVO {

    @ExcelProperty(value = "数采源")
    private String sourceType;

    @ExcelProperty(value = "数采源ip")
    private String dataSourceIp;

    @ExcelProperty(value = "主键id")
    private Long id;

    @ExcelProperty(value = "名称")
    private String name;

    @ExcelProperty(value = "编号")
    private String code;

    @ExcelProperty(value = "门牌号")
    private String doorplate;

    @ExcelProperty(value = "楼栋code")
    private String buildingCode;

    @ExcelProperty(value = "单元code")
    private String unitCode;

    @ExcelProperty(value = "楼层")
    private String floor;

    @ExcelProperty(value = "启用状态1开启，0禁用")
    private Integer enableStatus;

    @ExcelProperty(value = "户型主键id")
    private Long templateId;

    @ExcelProperty(value = "房间号")
    private String roomNo;


    @ExcelProperty(value = "项目Id")
    private Long projectId;

    @ExcelProperty(value = "楼盘ID")
    private Long realestateId;


    @ExcelProperty(value = "权限路径")
    private String path;

    @ExcelProperty(value = "权限路径1 楼盘id/项目id/楼栋/单元/家庭id")
    private String path1;

    @ExcelProperty(value = "权限路径2 楼盘id/楼栋/单元/家庭id")
    private String path2;

    @ExcelProperty(value = "权限路径名称")
    private String pathName;

    @ExcelProperty(value = "大屏通信Mac")
    private String screenMac;

    @ExcelProperty(value = "楼栋名称")
    private String buildingName;

    @ExcelProperty(value = "单元名称")
    private String unitName;

    @ExcelProperty(value = "创建人")
    private String createUser;





}
