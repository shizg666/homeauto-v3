package com.landleaf.homeauto.common.enums.jg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/8/16 9:25
 * @description: 短信发送类别枚举
 */
public enum JgSmsTypeEnum {


    /**
     * 登陆注册等
     */
    REGISTER_LOGIN(1, 168680),

    /**
     * 修改密码等
     */
    RESET(2, 168704),

    /**
     * 家庭组新增成员
     */
    FAMILY_NEW_ONE(3, 170867),
    ;

    private static Map<Integer, JgSmsTypeEnum> CODE_TYPE_MAP = new ConcurrentHashMap<>();
    static {
        for (JgSmsTypeEnum cte: JgSmsTypeEnum.values()){
            CODE_TYPE_MAP.put(cte.msgType, cte);
        }
    }

    private Integer msgType;

    private Integer tempId;

    JgSmsTypeEnum(Integer msgType, Integer tempId) {
        this.msgType = msgType;
        this.tempId = tempId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getTempId() {
        return tempId;
    }

    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

    /**
     * 获取枚举
     * @param msgType
     * @return
     */
    public static JgSmsTypeEnum getCodeTypeEnum(Integer msgType){
        return CODE_TYPE_MAP.get(msgType);
    }
}
