package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.mqtt.MqttUser;
import com.landleaf.homeauto.center.device.model.mapper.MqttUserMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMqttUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家庭组表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
@Slf4j
@Service
public class MqttUserImpl extends ServiceImpl<MqttUserMapper, MqttUser> implements IMqttUserService {


    @Override
    public void removeByFamilyCode(String code) {
        this.baseMapper.removeByFamilyCode(code);
    }

    @Override
    public void removeByFamilyCode(List<String> codes) {
        List<Long> ids = this.baseMapper.getIdsByFamilyCodes(codes);
        removeByIds(ids);
    }


}
