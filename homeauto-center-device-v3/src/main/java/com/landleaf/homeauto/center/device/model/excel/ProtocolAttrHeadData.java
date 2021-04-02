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
public class ProtocolAttrHeadData {
    @ExcelProperty("属性名称")
    private String name;

    @ExcelProperty("通信编码")
    private String code;

    @ExcelProperty("读写")
    private String operateAcl;

    @ExcelProperty("属性类型")
    private String typeStr;

    @ExcelProperty("品类")
    private String categoryStr;



}
