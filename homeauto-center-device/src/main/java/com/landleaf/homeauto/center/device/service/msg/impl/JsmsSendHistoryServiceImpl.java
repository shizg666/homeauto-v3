package com.landleaf.homeauto.center.device.service.msg.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.mapper.msg.JsmsSendHistoryMapper;
import com.landleaf.homeauto.center.device.service.msg.IJsmsSendHistoryService;
import com.landleaf.homeauto.common.domain.po.device.jg.JsmsSendHistory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 极光短信验证码记录 服务实现类
 * </p>
 *
 * @author wyl
 * @since 2019-08-16
 */
@Service
public class JsmsSendHistoryServiceImpl extends ServiceImpl<JsmsSendHistoryMapper, JsmsSendHistory> implements IJsmsSendHistoryService {

}
