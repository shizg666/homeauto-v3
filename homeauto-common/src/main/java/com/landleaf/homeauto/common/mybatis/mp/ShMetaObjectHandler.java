package com.landleaf.homeauto.common.mybatis.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充配置项:插入语句时自动填充id与时间信息
 */
@Component
@Slf4j
public class ShMetaObjectHandler implements MetaObjectHandler {
    public static final String STRING_STR = "java.lang.String";


    /**
     * 新增时自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
//        Date now =new Date() ;

        Class<?> id = metaObject.getSetterType("id");
        if (id != null) {
            String idType = id.getName();
            if (this.getFieldValByName("id", metaObject) == null) {
                if (STRING_STR.equals(idType)) {
                    this.setFieldValByName("id", IdGeneratorUtil.getUUID32(), metaObject);
                }
            }
        }
//        this.setFieldValByName("delFlag", 0, metaObject);
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);


        //获取当前登陆用户
        String user = "1_1";
        HomeAutoToken token = TokenContext.getToken();
        if (token != null && StringUtils.isNotEmpty(token.getUserId())) {
            user = String.format("%s_%s", token.getUserId(), token.getUserType());
        }
        this.setFieldValByName("createUser", user, metaObject);
        this.setFieldValByName("updateUser", user, metaObject);

    }

    /**
     * 更新时自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
//        Date now =new Date() ;

        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        //获取当前登陆用户
        String user = "1_1";
        HomeAutoToken token = TokenContext.getToken();
        if (token != null && StringUtils.isNotEmpty(token.getUserId())) {
            user = String.format("%s_%s", token.getUserId(), token.getUserType());
        }
        this.setFieldValByName("updateUser", user, metaObject);


    }


}
