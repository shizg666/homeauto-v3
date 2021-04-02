package com.landleaf.homeauto.common.enums.jg;

/**
 * @author Lokiy
 * @date 2019/8/15 15:41
 * @description: 极光短信类型
 */
public enum JsmsTypeEnum {

    /**
     * 验证码
     */
    VCODE_SMS(1, "验证码短信"),

    NOTICE_SMS(2, "通知短信"),

    MARKETING_SMS(3, "营销短信")

    ;

    private int type;
    private String remark;

    JsmsTypeEnum(int type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
