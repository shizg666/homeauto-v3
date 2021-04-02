package com.landleaf.homeauto.common.enums.email;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/8/29 9:41
 * @description: 邮件类型
 */
public enum EmailMsgTypeEnum {


    /**
     * 直接发送邮件内容
     */
    EMAIL_MSG(0),

    /**
     * 其他邮件模板
     */

    /**
     * 邮箱验证码验证
     */
    EMAIL_CODE(1),

    /**
     * 邮箱默认密码
     */
    EMAIL_DEFAULT_PWD(2),



    ;

    private Integer type;


    EmailMsgTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private static Map<Integer, EmailMsgTypeEnum> EMAIL_MSG_TYPE_MAP = new ConcurrentHashMap<>();
    static {
        for (EmailMsgTypeEnum emte: EmailMsgTypeEnum.values()){
            EMAIL_MSG_TYPE_MAP.put(emte.type, emte);
        }
    }


    /**
     * 获取枚举
     * @param type
     * @return
     */
    public static EmailMsgTypeEnum getEmailMsgTypeEnum(Integer type){
        return EMAIL_MSG_TYPE_MAP.get(type);
    }
}
