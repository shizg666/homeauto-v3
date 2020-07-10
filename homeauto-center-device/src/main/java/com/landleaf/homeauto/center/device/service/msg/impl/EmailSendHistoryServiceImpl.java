package com.landleaf.homeauto.center.device.service.msg.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.mapper.msg.EmailSendHistoryMapper;
import com.landleaf.homeauto.center.device.service.msg.IEmailSendHistoryService;
import com.landleaf.homeauto.common.domain.po.device.email.EmailSendHistory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件发送消息记录 服务实现类
 * </p>
 */
@Service
public class EmailSendHistoryServiceImpl extends ServiceImpl<EmailSendHistoryMapper, EmailSendHistory> implements IEmailSendHistoryService {

}
