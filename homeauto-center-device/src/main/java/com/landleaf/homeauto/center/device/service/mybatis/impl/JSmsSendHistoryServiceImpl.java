package com.landleaf.homeauto.center.device.service.mybatis.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.JsmsSendHistoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IJSmsSendHistoryService;
import com.landleaf.homeauto.common.domain.po.device.jg.JsmsSendHistory;
import org.springframework.stereotype.Service;

/**
 * 极光短信验证码记录 服务实现类
 *
 * @author wyl
 * @since 2019-08-16
 */
@Service
public class JSmsSendHistoryServiceImpl extends ServiceImpl<JsmsSendHistoryMapper, JsmsSendHistory> implements IJSmsSendHistoryService {

}
