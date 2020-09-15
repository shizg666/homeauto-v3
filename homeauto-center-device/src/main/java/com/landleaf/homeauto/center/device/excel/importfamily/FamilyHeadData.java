package com.landleaf.homeauto.center.device.excel.importfamily;

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
public class FamilyHeadData {
    @ExcelProperty({"主标题", "家庭名称"})
    private String name;
    @ExcelProperty({"主标题", "户号"})
    private String roomNo;
    @ExcelProperty({"主标题", "室"})
    private String room;
    @ExcelProperty({"主标题", "网关MAC"})
    private String mac;
    @ExcelProperty({"主标题", "户型名称"})
    private String tempalteName;
}
