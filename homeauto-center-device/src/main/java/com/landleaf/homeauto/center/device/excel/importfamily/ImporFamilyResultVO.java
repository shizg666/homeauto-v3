package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出配置对象
 */
@Data
@ContentRowHeight(12)
@HeadRowHeight(20)
public class ImporFamilyResultVO implements Serializable {
    @ColumnWidth(10)
    @ExcelProperty({"失败列表", "行数"})
    private String row;
    @ColumnWidth(50)
    @ExcelProperty({"失败列表", "失败原因"})
    private String error;

}
