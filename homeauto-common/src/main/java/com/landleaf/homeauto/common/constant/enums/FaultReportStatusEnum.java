package com.landleaf.homeauto.common.constant.enums;


import com.alibaba.druid.util.StringUtils;

/**
 * 故障状态
 *
 * @author wenyilu
 */
public enum FaultReportStatusEnum {

    NOT_ACCEPTED("0", "尚未受理"),
    ACCEPTED("1", "受理中"),
    WAITING_REPLY("2", "等待回复"),
    SOLVED("3", "已解决"),
    CLOSED("99", "已关闭"),

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
        return NOT_ACCEPTED;
    }

}
