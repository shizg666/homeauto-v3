package com.landleaf.homeauto.contact.screen.client.enums;

/**
 * 错误码定义
 *
 * @author wenyilu
 */
public enum ContactScreenErrorCodeEnumConst {

    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数错误"),
    SYSTEM_ERROR(500, "系统错误"),


    ;

    private int code;

    private String msg;

    private ContactScreenErrorCodeEnumConst(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
