package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoProjectMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProjectService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectBuildingService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectSoftConfigService;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoProject;
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectBuilding;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectSoftConfig;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
import com.landleaf.homeauto.common.enums.category.BaudRateEnum;
import com.landleaf.homeauto.common.enums.realestate.ProjectTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class HomeAutoProjectServiceImpl extends ServiceImpl<HomeAutoProjectMapper, HomeAutoProject> implements IHomeAutoProjectService {

    @Autowired
    private IProjectBuildingService iProjectBuildingService;
    @Autowired
    private IProjectSoftConfigService iProjectSoftConfigService;

    @Override
    public Map<String,Integer> countByRealestateIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String,Integer> count = this.baseMapper.countByRealestateId(ids);
        if (count == null){
            return Maps.newHashMapWithExpectedSize(0);
        }
        return count;
    }

    @Override
    public void add(ProjectDTO request) {
        addCheck(request);
        HomeAutoProject project = BeanUtil.mapperBean(request,HomeAutoProject.class);
        project.setStatus(0);
        save(project);
    }

    private void addCheck(ProjectDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoProject>().eq(HomeAutoProject::getName,request.getName()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目名称已存在");
        }
    }

    @Override
    public void update(ProjectDTO request) {
        updateCheck(request);
        HomeAutoProject project = BeanUtil.mapperBean(request,HomeAutoProject.class);
        updateById(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        int count = iProjectBuildingService.count(new LambdaQueryWrapper<ProjectBuilding>().eq(ProjectBuilding::getProjectId,id));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目下现有楼栋存在");
        }
        removeById(id);
        iProjectSoftConfigService.remove(new LambdaQueryWrapper<ProjectSoftConfig>().eq(ProjectSoftConfig::getProjectId,id));
        //todo 删除其他
    }

    @Override
    public BasePageVO<ProjectVO> page(ProjectQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<ProjectVO> result = this.baseMapper.page(request);
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<ProjectVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }

    @Override
    public List<SelectedIntegerVO> types() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }




    private void updateCheck(ProjectDTO request) {
        HomeAutoProject project = getById(request.getId());
        if (project == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目id不存在");
        }
        if (request.getName().equals(project.getName())){
            return;
        }
        addCheck(request);
    }
}
