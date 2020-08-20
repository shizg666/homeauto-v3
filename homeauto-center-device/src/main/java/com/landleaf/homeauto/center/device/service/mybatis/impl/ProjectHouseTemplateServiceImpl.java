package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProjectHouseTemplateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplatePageVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectHouseTemplateDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
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
        addCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        save(template);
    }

    private void addCheck(ProjectHouseTemplateDTO request) {
        int count = count(new LambdaQueryWrapper<ProjectHouseTemplate>().eq(ProjectHouseTemplate::getName,request.getName()).eq(ProjectHouseTemplate::getProjectId,request.getProjectId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型已存在");
        }
    }

    @Override
    public void update(ProjectHouseTemplateDTO request) {
        updateCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        updateById(template);
    }

    private void updateCheck(ProjectHouseTemplateDTO request) {
        ProjectHouseTemplate template = getById(request.getId());
        if (template.getName().equals(template.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        //todo
        removeById(request.getId());
    }

    @Override
    public List<HouseTemplatePageVO> getListByProjectId(String id) {

        return null;
    }
}
