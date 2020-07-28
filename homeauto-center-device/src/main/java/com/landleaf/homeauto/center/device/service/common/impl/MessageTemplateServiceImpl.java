package com.landleaf.homeauto.center.device.service.common.impl;

import com.landleaf.homeauto.center.device.service.common.IMessageTemplateService;

import java.util.concurrent.TimeUnit;

/**
 * @author Yujiumin
 * @version 2020/7/28
 */
public class MessageTemplateServiceImpl implements IMessageTemplateService {

    @Override
    public Integer addTemplate(String subject, String content, Integer type, Integer ttl, TimeUnit timeUnit, String operator) {
        return null;
    }
}
