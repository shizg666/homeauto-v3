package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.MessageTemplateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMessageTemplateService;
import com.landleaf.homeauto.common.domain.po.device.MessageTemplatePO;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Yujiumin
 * @version 2020/7/28
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplatePO> implements IMessageTemplateService {

    @Override
    public Integer addTemplate(String subject, String content, Integer type, Integer ttl, TimeUnit timeUnit, String operator) {
        return null;
    }

    @Override
    public void logicDeleteTemplate(Integer id) {

    }

    @Override
    public void physicDeleteTemplate(Integer id) {

    }
}
