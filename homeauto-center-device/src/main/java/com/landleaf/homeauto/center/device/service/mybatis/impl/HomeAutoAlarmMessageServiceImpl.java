package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAlarmMessageMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAlarmMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报警信息 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-15
 */
@Service
public class HomeAutoAlarmMessageServiceImpl extends ServiceImpl<HomeAutoAlarmMessageMapper, HomeAutoAlarmMessageDO> implements IHomeAutoAlarmMessageService {

}
