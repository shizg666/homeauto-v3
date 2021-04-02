package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 工程批量导入配置对象
 */
@Data
public class ImportProtocolModel {

    @ExcelProperty(index = 0)
    private String name;

    @ExcelProperty(index = 1)
    private String code;

    @ExcelProperty(index = 2)
    private String categoryCode;

    @ExcelProperty(index = 3)
    private String valType;

    @ExcelProperty(index = 4)
    private String operateAcl;

    @ExcelProperty(index = 5)
    private String type;

    @ExcelProperty(index = 6)
    private String appFlag;

    @ExcelProperty(index = 7)
    private String val;

    @ExcelProperty(index = 8)
    private String unit;

    @ExcelProperty(index = 9)
    private String calculationFactor;

    @ExcelProperty(index = 10)
    private String precision;

    @ExcelProperty(index = 11)
    private String step;

    @ExcelProperty(index = 12)
    private String min;

    @ExcelProperty(index = 13)
    private String max;


    private String protocolId;

    private String attrId;


    private String row;
    private String error;


}
