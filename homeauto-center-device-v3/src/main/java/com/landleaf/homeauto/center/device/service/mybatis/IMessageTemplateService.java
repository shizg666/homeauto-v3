package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.device.MessageTemplatePO;

import java.util.concurrent.TimeUnit;

/**
 * 消息模板接口
 *
 * @author Yujiumin
 * @version 2020/7/28
 */
public interface IMessageTemplateService extends IService<MessageTemplatePO> {

    /**
     * 添加模板
     *
     * @param subject  主题
     * @param content  内容
     * @param type     消息类型
     * @param ttl      有效期
     * @param timeUnit 时间单位
     * @param operator 操作人
     * @return 主键
     */
    Integer addTemplate(String subject, String content, Integer type, Integer ttl, TimeUnit timeUnit, String operator);

    /**
     * 逻辑删除模板
     *
     * @param id 主键
     */
    void logicDeleteTemplate(Integer id);

    /**
     * 物理删除模板
     *
     * @param id 主键
     */
    void physicDeleteTemplate(Integer id);




}
