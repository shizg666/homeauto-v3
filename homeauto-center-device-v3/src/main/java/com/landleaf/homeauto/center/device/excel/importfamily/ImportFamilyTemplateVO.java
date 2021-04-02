package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备故障导出配置对象
 */
@Data
@ContentRowHeight(12)
@HeadRowHeight(20)
@ColumnWidth(25)
public class ImportFamilyTemplateVO {

    @ExcelProperty(value = "名称")
    private String name;

    @ExcelProperty(value = "楼栋code")
    private String buildingCode;

    @ExcelProperty(value = "单元code")
    private String unitCode;

    @ExcelProperty(value = "门牌")
    private String roomNo;

    @ExcelProperty(value = "主大屏ip")
    private String ip;

    @ExcelProperty(value = "主大屏Mac")
    private String screenMac;

    @ExcelProperty(value = "户型")
    private String templates;










}
