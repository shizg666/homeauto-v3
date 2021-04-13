package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.ProjectSoftConfigMapper;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyModeScopeVO;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectSoftConfigService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectSoftConfig;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectSoftConfigDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        update(projectSoftConfig,new LambdaUpdateWrapper<ProjectSoftConfig>().eq(ProjectSoftConfig::getCode,request.getCode()).eq(ProjectSoftConfig::getProjectId,request.getProjectId()));
    }

    @Override
    public List<ProjectSoftConfigDTO> getConfigByProjectId(String id) {
        return this.baseMapper.getConfigByProjectId(id);
    }
    /**
     * 获取项目下不同模式下温度控制范围
     * @param projectId  项目ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyModeScopeVO>
     * @author wenyilu
     * @date  2021/1/6 13:37
     */
    @Override
    public List<FamilyModeScopeVO> getFamilyModeTempScopeConfig(Long projectId) {
        List<FamilyModeScopeVO> result = Lists.newArrayList();
        QueryWrapper<ProjectSoftConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id",projectId);
        queryWrapper.eq("biz_type",5);
        queryWrapper.eq("sys_type",1);
        List<ProjectSoftConfig> list = list(queryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            for (ProjectSoftConfig projectSoftConfig : list) {
                String value = projectSoftConfig.getValue();
                FamilyModeScopeVO familyModeScopeVO = new FamilyModeScopeVO();
                familyModeScopeVO.setCode(projectSoftConfig.getCode());
                familyModeScopeVO.setMin(value.split("-")[0]);
                familyModeScopeVO.setMax(value.split("-")[1]);
                result.add(familyModeScopeVO);
            }
        }
        return result;
    }
}
