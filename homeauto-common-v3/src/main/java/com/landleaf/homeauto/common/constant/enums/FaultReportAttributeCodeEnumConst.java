package com.landleaf.homeauto.common.constant.enums;

/**
 * 故障报修对应属性编码
 *
 * @author wenyilu
 */
public enum FaultReportAttributeCodeEnumConst {

    REPAIR_TIME("repairTime", "报修时间"),
    REPAIR_APPEARANCE("repairAppearance", "报修现象"),
    CUSTOMER_FEEDBACK("customerFeedback", "客户反馈"),
    DEAL_USER("dealUser", "处理人员"),

    ;


    private String code;

    private String msg;

    FaultReportAttributeCodeEnumConst(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
