package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.ThirdFamilySceneIcon;
import com.landleaf.homeauto.center.device.model.mapper.ThirdFamilySceneIconMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IThirdFamilySceneIconService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 三方家庭场景icon表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-07-02
 */
@Service
public class ThirdFamilySceneIconServiceImpl extends ServiceImpl<ThirdFamilySceneIconMapper, ThirdFamilySceneIcon> implements IThirdFamilySceneIconService {

    @Override
    public String getIconBySceneId(Long sceneId) {
        return this.baseMapper.getIconBySceneId(sceneId);
    }
}
