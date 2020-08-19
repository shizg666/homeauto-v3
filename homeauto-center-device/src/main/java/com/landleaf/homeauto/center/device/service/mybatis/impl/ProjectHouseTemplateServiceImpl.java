package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProjectHouseTemplateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectHouseTemplateDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectHouseTemplateVO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ProjectHouseTemplateVO> getListByProjectId(String id) {

        return null;
    }
}
