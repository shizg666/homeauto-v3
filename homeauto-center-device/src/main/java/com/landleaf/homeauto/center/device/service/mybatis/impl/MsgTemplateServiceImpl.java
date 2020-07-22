package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.MsgTemplateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgTemplateService;
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
