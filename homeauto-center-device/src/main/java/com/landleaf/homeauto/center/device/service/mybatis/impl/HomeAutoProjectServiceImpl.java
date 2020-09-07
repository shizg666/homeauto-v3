package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.IdentityAuthenticator;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoAddress;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoProjectMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuilding;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectSoftConfig;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.enums.realestate.ProjectTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
@Slf4j
public class HomeAutoProjectServiceImpl extends ServiceImpl<HomeAutoProjectMapper, HomeAutoProject> implements IHomeAutoProjectService {

    @Autowired
    private IProjectBuildingService iProjectBuildingService;
    @Autowired
    private IProjectSoftConfigService iProjectSoftConfigService;
    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    @Autowired
    private CommonService commonService;

    @Override
    public Map<String, Integer> countByRealestateIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        List<RealestateCountBO> countList = this.baseMapper.countByRealestateId(ids);
        Map<String, Integer> count = null;
        if (!CollectionUtils.isEmpty(countList)) {
            count = countList.stream().collect(Collectors.toMap(RealestateCountBO::getRealestateId, RealestateCountBO::getCount));
        }
        if (count == null) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        return count;
    }

    @Override
    public void add(ProjectDTO request) {
        addCheck(request);
        HomeAutoProject project = BeanUtil.mapperBean(request, HomeAutoProject.class);
        project.setStatus(0);
        HomeAutoRealestate realestate = iHomeAutoRealestateService.getById(request.getRealestateId());
        project.setId(IdGeneratorUtil.getUUID32());
        project.setPath(realestate.getPathOauth().concat("/").concat(project.getId()));
        save(project);
    }

    private void addCheck(ProjectDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoProject>().eq(HomeAutoProject::getName, request.getName()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目名称已存在");
        }
    }

    @Override
    public void update(ProjectDTO request) {
        updateCheck(request);
        HomeAutoProject project = BeanUtil.mapperBean(request, HomeAutoProject.class);
        updateById(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        int count = iProjectBuildingService.count(new LambdaQueryWrapper<ProjectBuilding>().eq(ProjectBuilding::getProjectId, id));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目下现有楼栋存在");
        }
        removeById(id);
        iProjectSoftConfigService.remove(new LambdaQueryWrapper<ProjectSoftConfig>().eq(ProjectSoftConfig::getProjectId, id));
        //todo 删除其他
    }

    @Override
    public BasePageVO<ProjectVO> page(ProjectQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<String> path = commonService.getUserPathScope();
//        List<String> path = Lists.newArrayListWithExpectedSize(1);
//        path.add("CN");
        //todo
        path.forEach(o->{
            log.info(o);
        });
        request.setPaths(path);
        List<ProjectVO> result = this.baseMapper.page(request);
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<ProjectVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
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

    @Override
    public void statusSwitch(ProjectStatusDTO projectStatusDTO) {
        HomeAutoProject project = BeanUtil.mapperBean(projectStatusDTO, HomeAutoProject.class);
        updateById(project);
    }

    @Override
    public List<CascadeVo> getListPathProjects(boolean fliter) {
        List<String> path = null;
        if(fliter){
            path = commonService.getUserPathScope();
        }
        Map<String, String> data = new HashMap<>();
        Map<String, Set<String>> provices = new HashMap<>();
        Map<String, Set<String>> cities = new HashMap<>();
        Map<String, Set<String>> areas = new HashMap<>();
        Map<String, Set<String>> realestates = new HashMap<>();
        Map<String, List<CascadeVo>> projects = new HashMap<>();
        List<ProjectPathVO> pathVOS = this.baseMapper.getListPathProjects(path);
        pathVOS.forEach(v -> {
            data.put(v.getCountryCode(), v.getCountry());
            data.put(v.getProvinceCode(), v.getProvince());
            data.put(v.getCityCode(), v.getCity());
            data.put(v.getAreaCode(), v.getArea());
            data.put(v.getRealestateId(), v.getRealestateName());
            Set<String> proviceSet = provices.get(v.getCountryCode());
            if (proviceSet == null) {
                proviceSet = new HashSet<>();
                provices.put(v.getCountryCode(), proviceSet);
            }
            proviceSet.add(v.getProvinceCode());
            Set<String> citySet = cities.get(v.getProvinceCode());
            if (citySet == null) {
                citySet = new HashSet<>();
                cities.put(v.getProvinceCode(), citySet);
            }
            citySet.add(v.getCityCode());

            Set<String> areaSet = areas.get(v.getCityCode());
            if (areaSet == null) {
                areaSet = new HashSet<>();
                areas.put(v.getCityCode(), areaSet);
            }
            areaSet.add(v.getAreaCode());

            Set<String> realestateSet = realestates.get(v.getAreaCode());
            if (realestateSet == null) {
                realestateSet = new HashSet<>();
                realestates.put(v.getAreaCode(), realestateSet);
            }
            realestateSet.add(v.getRealestateId());

            List<CascadeVo> projectList = projects.get(v.getRealestateId());
            if (projectList == null) {
                projectList = new ArrayList<>();
                projects.put(v.getRealestateId(), projectList);
            }
            projectList.add(new CascadeVo( v.getProjectName(),v.getProjectId()));
        });

        List<CascadeVo> vos = new ArrayList<>();
        provices.forEach((countryCode, proviceList) -> {
            List<CascadeVo> provinceVos = new ArrayList<>();
            proviceList.forEach(proviceCode -> {
                List<CascadeVo> cityVos = new ArrayList<>();
                Set<String> citySet = cities.get(proviceCode);
                citySet.forEach(cityCode -> {
                    List<CascadeVo> areaVos = new ArrayList<>();
                    Set<String> areaSet = areas.get(cityCode);
                    areaSet.forEach(areaCode -> {
                        List<CascadeVo> realestateVos = new ArrayList<>();
                        Set<String> ealestateSet = realestates.get(areaCode);
                        ealestateSet.forEach(realestateCode -> {
                            realestateVos.add(new CascadeVo(data.get(realestateCode), realestateCode, projects.get(realestateCode)));
                        });
                        areaVos.add(new CascadeVo(data.get(areaCode), areaCode, realestateVos));
                    });
                    cityVos.add(new CascadeVo(data.get(cityCode), cityCode, areaVos));
                });
                provinceVos.add(new CascadeVo(data.get(proviceCode), proviceCode, cityVos));
            });
            vos.add(new CascadeVo(data.get(countryCode), countryCode, provinceVos));
        });
        return vos;
    }

    @Override
    public List<SelectedVO> getListSeclects() {
        List<String> path = commonService.getUserPathScope();
        return this.baseMapper.getListSeclects(path);
    }


    @Override
    public List<CascadeVo> getListCascadeSeclects() {
        List<String> path = commonService.getUserPathScope();
//        List<String> path = Lists.newArrayListWithExpectedSize(1);
//        path.add("CN");
        List<HomeAutoProject> projects = this.baseMapper.getListCascadeSeclects(path);
        if (CollectionUtils.isEmpty(projects)){
            return null;
        }
        Map<String,List<HomeAutoProject>> mapData = projects.stream().collect(Collectors.groupingBy(HomeAutoProject::getRealestateId));

        List<String> ids = projects.stream().map(HomeAutoProject::getRealestateId).collect(Collectors.toList());
        List<CascadeVo> data = iHomeAutoRealestateService.getListCascadeSeclects(ids);
        data.forEach(obj->{
            List<HomeAutoProject> dataProjects = mapData.get(obj.getValue());
            if (!CollectionUtils.isEmpty(dataProjects)){
                List<CascadeVo> projectData= Lists.newArrayListWithCapacity(dataProjects.size());
                dataProjects.forEach(project->{
                    CascadeVo cascadeVo = new CascadeVo(project.getName(),project.getId());
                    projectData.add(cascadeVo);
                });
                obj.setChildren(projectData);
            }
        });
        return data;
    }



    private void updateCheck(ProjectDTO request) {
        HomeAutoProject project = getById(request.getId());
        if (project == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目id不存在");
        }
        if (!project.getRealestateId().equals(request.getRealestateId())) {
            HomeAutoRealestate realestate = iHomeAutoRealestateService.getById(request.getRealestateId());
            request.setPath(realestate.getPathOauth().concat("/").concat(project.getId()));
        }
        if (request.getName().equals(project.getName())) {
            return;
        }
        addCheck(request);
    }
}
