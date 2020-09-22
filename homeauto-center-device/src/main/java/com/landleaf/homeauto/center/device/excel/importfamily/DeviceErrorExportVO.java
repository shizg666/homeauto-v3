package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
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




    @ExcelProperty("产品名称")
    private String productName;

    @ExcelProperty("故障信息")
    private String faultMsg;

    @ExcelProperty("参考值")
    private String reference;

    @ExcelProperty("当前值")
    private String current;


    @ExcelProperty(value = "故障时间")
    private String createTimeStr;

    @ExcelProperty("所属楼盘")
    private String realestateName;

    @ExcelProperty("所属项目")
    private String projectName;

    @ExcelProperty("家庭名称")
    private String familyName;

    @ExcelProperty("故障状态")
    private String faultStatusStr;




}
