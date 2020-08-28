package com.landleaf.homeauto.common.constant.enums;


import com.alibaba.druid.util.StringUtils;

/**
 * 故障状态
 *
 * @author wenyilu
 */
public enum FaultReportStatusEnum {

    PENDING("0", "待受理"),
    ACCEPTED("1", "受理中"),
    SOLVED("3", "已维修"),
    CLOSED("99", "已完成"),

    ;


    private String code;

    private String msg;

    FaultReportStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public static FaultReportStatusEnum getStatusByCode(String code) {
        FaultReportStatusEnum[] values = FaultReportStatusEnum.values();
        for (FaultReportStatusEnum value : values) {
            if (StringUtils.equals(value.getCode(), code)) {
                return value;
            }
        }
        return PENDING;
    }

}
