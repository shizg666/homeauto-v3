package com.landleaf.homeauto.common.config.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.TokenContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自动填充配置项:插入语句时自动填充id与时间信息
 */
@Component
@Slf4j
public class ShMetaObjectHandler implements MetaObjectHandler {


    /**
     * 新增时自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>start insert fill<<<<<<<<<<<<<<<<<<<<<<<<<");
//        Date now =new Date() ;

        this.setFieldValByName("id", IdGeneratorUtil.getUUID32(), metaObject);
        this.setFieldValByName("delFlag", 0, metaObject);
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);


        //获取当前登陆用户
//        this.setFieldValByName("createUser", getUser(), metaObject);
//        this.setFieldValByName("updateUser", getUser(), metaObject);

        this.setFieldValByName("createUser", TokenContextUtil.getUser(), metaObject);
        this.setFieldValByName("updateUser", TokenContextUtil.getUser(), metaObject);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>end insert fill<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    /**
     * 更新时自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>start update fill<<<<<<<<<<<<<<<<<<<<<<<<<");
//        Date now =new Date() ;

        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

        //获取当前登陆用户
//        this.setFieldValByName("updateUser", getUser(), metaObject);
        this.setFieldValByName("updateUser", TokenContextUtil.getUser(), metaObject);


        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>end update fill<<<<<<<<<<<<<<<<<<<<<<<<<");
    }


}
