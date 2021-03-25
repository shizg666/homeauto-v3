package com.landleaf.homeauto.common.enums.protocol;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 协议场景类型
 *
 * @author wenyilu
 */
public enum ProtocolTypeEnum {

    /**
     * 终端:大屏
     */
    HVAC(1, "暖通"),

    /**
     * 终端:网关
     */
    SMART_HOME(2, "智能家居"),
    /**
     * 终端:网关
     */
    SMART_COMMUNITY(3, "智能社区"),
    /**
     * 终端:网关
     */
    OTHER(4, "其他");

    private Integer code;

    private String name;

    ProtocolTypeEnum(Integer code, String name) {
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


    private static Map<Integer, ProtocolTypeEnum> map = Maps.newHashMapWithExpectedSize(ProtocolTypeEnum.values().length);
    static {
        for(ProtocolTypeEnum enu : ProtocolTypeEnum.values()){
            map.put(enu.getCode(), enu);
        }
    }
    public static ProtocolTypeEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        ProtocolTypeEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
