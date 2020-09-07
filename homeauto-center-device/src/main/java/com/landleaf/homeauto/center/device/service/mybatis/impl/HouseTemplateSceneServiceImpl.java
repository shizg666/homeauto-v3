package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.mapper.HouseTemplateSceneMapper;
import com.landleaf.homeauto.center.device.model.vo.project.HouseSceneDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 户型情景表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Service
public class HouseTemplateSceneServiceImpl extends ServiceImpl<HouseTemplateSceneMapper, HouseTemplateScene> implements IHouseTemplateSceneService {

    @Override
    public void add(HouseSceneDTO request) {

    }

    @Override
    public void update(HouseSceneDTO request) {

    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }
}
