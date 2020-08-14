package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProjectHouseTemplateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectHouseTemplateDTO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目户型表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectHouseTemplateServiceImpl extends ServiceImpl<ProjectHouseTemplateMapper, ProjectHouseTemplate> implements IProjectHouseTemplateService {

    @Override
    public void add(ProjectHouseTemplateDTO request) {

    }

    @Override
    public void update(ProjectHouseTemplateDTO request) {

    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }
}
