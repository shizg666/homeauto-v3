package com.landleaf.homeauto.center.device.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * 导出配置对象
 */
@Data
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class ProtocoltHeadData {
    @ExcelProperty("属性名称")
    private String name;

    @ExcelProperty("通信编码")
    private String code;

    @ExcelProperty("品类")
    private String categoryCode;

    @ExcelProperty("属性值类型")
    private String valType;

    @ExcelProperty("读写")
    private String operateAcl;

    @ExcelProperty("属性类型")
    private String type;

    @ExcelProperty("App读写")
    private String appFlag;
    @ExcelProperty("值")
    private String val;
    @ExcelProperty("单位")
    private String unit;
    @ExcelProperty("计算系数")
    private String calculationFactor;
    @ExcelProperty("精度")
    private String precision;
    @ExcelProperty("步长")
    private String step;
    @ExcelProperty("最小值")
    private String min;
    @ExcelProperty("最大值")
    private String max;
}
