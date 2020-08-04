package com.landleaf.homeauto.contact.screen.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 与大屏通讯指令类别
 *
 * @author wenyilu
 */
public enum ContactScreenNameSpaceEnum {

    HOMEAUTO_CONTROL("HomeAuto.Control", "控制消息"),

    HOMEAUTO_NOTICE("HomeAuto.Notice", "通知消息"),

    HOMEAUTO_CONFIG_REQUEST("HomeAuto.Config.Request", "配置信息请求"),

    HOMEAUTO_EVENT("HomeAuto.Event", "事件"),

    HOMEAUTO_OTHER_REQUEST("HomeAuto.Other.Request", "其它请求"),
    ;

    private String code;
    private String desc;


    ContactScreenNameSpaceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static ContactScreenNameSpaceEnum getByCode(String namespace) {
        for (ContactScreenNameSpaceEnum nameSpaceEnum :ContactScreenNameSpaceEnum.values()) {
            String code = nameSpaceEnum.getCode();
            if(StringUtils.equals(code,namespace)){
                return nameSpaceEnum;
            }
        }
        return null;

    }
}
