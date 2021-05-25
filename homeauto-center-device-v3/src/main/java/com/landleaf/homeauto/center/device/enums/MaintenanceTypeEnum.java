package com.landleaf.homeauto.center.device.enums;//package com.landleaf.homeauto.center.device.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 维保记录Type
 *
 * @author pilo
 */
public enum MaintenanceTypeEnum {

    /**
     * 手动输入
     */
    input(1,"手动输入"),

    /**
     * 移动端上报
     */
    report(2,"移动端上报");


    private Integer code;

    private String desc;

    MaintenanceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private static Map<Integer, MaintenanceTypeEnum> map = null; // type, enum映射
    private static boolean isInit = false;
    public static MaintenanceTypeEnum getInstByType(Integer code) {
        if (code == null) {
            return null;
        }
        if (!isInit) {
            synchronized (MaintenanceTypeEnum.class) {
                if (!isInit) {
                    map = new HashMap<Integer, MaintenanceTypeEnum>();
                    for (MaintenanceTypeEnum enu : MaintenanceTypeEnum.values()) {
                        map.put(enu.getCode(), enu);
                    }
                }
                isInit = true;
            }

        }
        MaintenanceTypeEnum pojoEnum = map.get(code);
        return pojoEnum;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
