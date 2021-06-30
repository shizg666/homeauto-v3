package com.landleaf.homeauto.contact.screen.common.enums;

/**
 * 错误码定义
 *
 * @author wenyilu
 */
public enum ContactScreenErrorCodeEnumConst {
    /**
     * 错误码定义
     */
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数错误"),
    SYSTEM_ERROR(500, "系统错误"),
    FAMILY_NOT_EXIST(4001, "家庭不存在"),
    MAC_ALREADY_BIND(4002, "mac地址已被其它家庭绑定"),
    FAMILY_NOT_FOUND(4003, "参数不正确，该家庭不存在"),


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
