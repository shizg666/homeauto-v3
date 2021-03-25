package com.landleaf.homeauto.center.device.handle.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 自定义拦截器.对第一列第一行和第二行的数据新增下拉框，显示 测试1 测试2
 *
 * @author Jiaju Zhuang
 */
@Slf4j
@Component
public class ProtocolSheetWriteHandler implements SheetWriteHandler {

    //key是需要添加下拉框的列的index value是下拉框数据
    private Map<Integer,String[]> data;

    public ProtocolSheetWriteHandler(Map<Integer,String[]> data){
        this.data = data;
    }

    public ProtocolSheetWriteHandler(){

    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        //获取工作簿
        Sheet sheet = writeSheetHolder.getSheet();
        ///开始设置下拉框
        DataValidationHelper helper = sheet.getDataValidationHelper();
        //设置下拉框
        for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
            /*起始行、终止行、起始列、终止列  起始行为1即表示表头不设置**/
            CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, entry.getKey(), entry.getKey());
            /*设置下拉框数据**/
            DataValidationConstraint constraint = helper.createExplicitListConstraint(entry.getValue());
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            sheet.addValidationData(dataValidation);
        }



    }





}
