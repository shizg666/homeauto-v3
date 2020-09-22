package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 设备故障导出配置对象
 */
@Data
@ContentRowHeight(12)
@HeadRowHeight(20)
@ColumnWidth(25)
public class DeviceErrorExportVO {
    @ColumnWidth(10)
    @ExcelProperty({"失败列表", "行数"})
    private String row;
    @ColumnWidth(50)
    @ExcelProperty({"失败列表", "失败原因"})
    private String error;

    @ExcelProperty("日期标题")
    private Date date;




    @ExcelProperty("产品名称")
    private String productName;

    @ExcelProperty("故障信息")
    private String faultMsg;

    @ExcelProperty(value = "故障时间")
    private LocalDateTime createTime;

    @ExcelProperty("所属楼盘")
    private String realestateName;

    @ExcelProperty("所属项目")
    private String projectName;

    @ExcelProperty("家庭名称")
    private String familyName;

    @ExcelProperty("故障状态")
    private String faultStatusStr;

    @ExcelProperty("参考值")
    private String reference;

    @ExcelProperty("当前值")
    private String current;



}
