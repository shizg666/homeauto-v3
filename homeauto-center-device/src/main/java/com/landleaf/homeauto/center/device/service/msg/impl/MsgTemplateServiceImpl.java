package com.landleaf.homeauto.center.device.service.msg.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.mapper.msg.MsgTemplateMapper;
import com.landleaf.homeauto.center.device.service.msg.IMsgTemplateService;
import com.landleaf.homeauto.common.domain.po.device.email.MsgTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息模板表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-29
 */
@Service
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateMapper, MsgTemplate> implements IMsgTemplateService {

}
