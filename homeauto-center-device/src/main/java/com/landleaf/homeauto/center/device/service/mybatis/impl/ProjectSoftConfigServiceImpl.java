package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProjectSoftConfigMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectSoftConfigService;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectSoftConfig;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectSoftConfigDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目软件配置表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectSoftConfigServiceImpl extends ServiceImpl<ProjectSoftConfigMapper, ProjectSoftConfig> implements IProjectSoftConfigService {

    @Override
    public void add(ProjectSoftConfigDTO request) {
        ProjectSoftConfig projectSoftConfig = BeanUtil.mapperBean(request,ProjectSoftConfig.class);
        save(projectSoftConfig);
    }

    @Override
    public void update(ProjectSoftConfigDTO request) {
        ProjectSoftConfig projectSoftConfig = BeanUtil.mapperBean(request,ProjectSoftConfig.class);
        updateById(projectSoftConfig);
    }

    @Override
    public List<ProjectSoftConfigDTO> getConfigByProjectId(String id) {
        return this.baseMapper.getConfigByProjectId(id);
    }
}
