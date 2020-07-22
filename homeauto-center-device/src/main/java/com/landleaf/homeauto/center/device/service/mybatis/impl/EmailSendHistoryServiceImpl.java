package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.EmailSendHistoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IEmailSendHistoryService;
import com.landleaf.homeauto.common.domain.po.device.email.EmailSendHistory;
import org.springframework.stereotype.Service;

/**
 * 邮件发送消息记录 服务实现类
 *
 * @author wenyilu
 */
@Service
public class EmailSendHistoryServiceImpl extends ServiceImpl<EmailSendHistoryMapper, EmailSendHistory> implements IEmailSendHistoryService {

}
