package com.landleaf.homeauto.common.enums.protocol;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 协议属性值类型
 *
 * @author wenyilu
 */
public enum ProtocolAttrValTypeEnum {


    SELECT(1, "枚举"),
    VALUE(2, "数值"),
    BIT(3, "二进制");

    private Integer code;

    private String name;

    ProtocolAttrValTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private static Map<Integer, ProtocolAttrValTypeEnum> map = Maps.newHashMapWithExpectedSize(ProtocolAttrValTypeEnum.values().length);
    static {
        for(ProtocolAttrValTypeEnum enu : ProtocolAttrValTypeEnum.values()){
            map.put(enu.getCode(), enu);
        }
    }
    public static ProtocolAttrValTypeEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        ProtocolAttrValTypeEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
