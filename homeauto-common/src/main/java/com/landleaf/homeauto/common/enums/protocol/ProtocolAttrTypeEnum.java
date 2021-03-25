package com.landleaf.homeauto.common.enums.protocol;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 协议属性类型
 *
 * @author wenyilu
 */
public enum ProtocolAttrTypeEnum {

    OTHER(0, "其他"),
    FEATURES(1, "功能"),
    ERROR(2, "故障"),
    STATUS(3, "状态"),
    LOGIC(4, "逻辑");

    private Integer code;

    private String name;

    ProtocolAttrTypeEnum(Integer code, String name) {
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


    private static Map<Integer, ProtocolAttrTypeEnum> map = Maps.newHashMapWithExpectedSize(ProtocolAttrTypeEnum.values().length);
    static {
        for(ProtocolAttrTypeEnum enu : ProtocolAttrTypeEnum.values()){
            map.put(enu.getCode(), enu);
        }
    }
    public static ProtocolAttrTypeEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        ProtocolAttrTypeEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
