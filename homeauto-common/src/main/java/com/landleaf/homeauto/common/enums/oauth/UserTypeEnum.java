package com.landleaf.homeauto.common.enums.oauth;


import com.google.common.collect.Maps;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.enums.BaseEnum;

import java.util.Map;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.*;

/**
 * 登录用户类型
 */
public enum UserTypeEnum implements BaseEnum {
    WEB(1, "后台账号", "/controller", SysUser.class,"userId"),
    APP(2, "APP", "/app", HomeAutoAppCustomer.class,"userId"),
    APP_NO_SMART(4, "自由方舟APP", "/app", HomeAutoAppCustomer.class,"userId"),
    WECHAT(3, "WECHAT", "/wechat", HomeAutoAppCustomer.class,"openId");

    public final int type;
    public String name;
    /**
     * 客户端请求路径前缀
     */
    public String path;

    /**
     * 对应实体类型
     */
    public Class entityClazz;

    /**
     * 唯一标记属性名
     */
    private String uniquePropertyName;

    public static Map<Integer, String> redisCacheMap = Maps.newHashMap();
    public static Map<Integer, UserTypeEnum> userTypeEnumMap = Maps.newHashMap();


    static {
        redisCacheMap.put(UserTypeEnum.WEB.getType(), KEY_USER_INFO);
        redisCacheMap.put(UserTypeEnum.APP.getType(), KEY_CUSTOMER_INFO);
        redisCacheMap.put(UserTypeEnum.WECHAT.getType(), KEY_WECHAT_CUSTOMER_INFO);
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            userTypeEnumMap.put(userTypeEnum.getType(), userTypeEnum);
        }
    }


    UserTypeEnum(int type, String name, String path, Class entityClazz,String uniquePropertyName ) {
        this.type = type;
        this.name = name;
        this.path = path;
        this.entityClazz = entityClazz;
        this.uniquePropertyName = uniquePropertyName;
    }

    public static UserTypeEnum getEnumByType(Integer type) {
        if (type == null) {
            return WEB;
        }
        UserTypeEnum[] values = UserTypeEnum.values();
        for (UserTypeEnum value : values) {
            if (value.getType() == type.intValue()) {
                return value;
            }
        }
        return WEB;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class getEntityClazz() {
        return entityClazz;
    }

    public void setEntityClazz(Class entityClazz) {
        this.entityClazz = entityClazz;
    }

    public String getUniquePropertyName() {
        return uniquePropertyName;
    }

    public void setUniquePropertyName(String uniquePropertyName) {
        this.uniquePropertyName = uniquePropertyName;
    }
}
