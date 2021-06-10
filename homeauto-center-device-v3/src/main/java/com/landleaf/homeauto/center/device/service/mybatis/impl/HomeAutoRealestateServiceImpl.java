package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.EnergyModeEnum;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoRealestateMapper;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeQryDTO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeStatusVO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeUpdateVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProjectService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoRealestateService;
import com.landleaf.homeauto.center.device.service.mybatis.IBizNumProducerService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeLongVo;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.enums.realestate.ProjectTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 楼盘表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
@Slf4j
public class HomeAutoRealestateServiceImpl extends ServiceImpl<HomeAutoRealestateMapper, HomeAutoRealestate> implements IHomeAutoRealestateService {
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;
    @Autowired
    private IBizNumProducerService iBizNumProducerService;
    @Autowired
    private CommonServiceImpl commonService;
    @Autowired
    private IdService idService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;


    @Override
    public void add(RealestateDTO request) {
        addcheck(request);
        // CN/610000/610100/610112/f4f711c4e9724f4b978a2d698ecbaf7f
        // 中国/陕西省/西安市/未央区/上实
        HomeAutoRealestate realestate = BeanUtil.mapperBean(request,HomeAutoRealestate.class);
        realestate.setId(idService.getSegmentId());
        buildPath(realestate);
        String numStr =  iBizNumProducerService.getRealestateNum(realestate.getAreaCode());
        realestate.setCode(numStr);
        save(realestate);
    }

    private void buildPath(HomeAutoRealestate request) {
        String[]  path = request.getPath().split("/");
        if (path == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "地址格式不");
        }
        if (path.length != 4) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "地址格式不对");
        }

        String[]  pathName = request.getPathName().split("/");
        if (pathName == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "地址格式不");
        }
        if (pathName.length != 4) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "地址格式不对");
        }
        request.setAddressComplete(request.getPathName().concat("/").concat(request.getAddress()));
        request.setPathOauth(request.getPath().concat("/").concat(String.valueOf(request.getId())));
        request.setProvinceCode(path[1]);
        request.setCityCode(path[2]);
        request.setAreaCode(path[3]);
        request.setCountryCode(path[0]);
        request.setProvince(pathName[1]);
        request.setCity(pathName[2]);
        request.setArea(pathName[3]);
        request.setCountry(pathName[0]);
    }


    private void addcheck(RealestateDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoRealestate>().eq(HomeAutoRealestate::getName,request.getName()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "名称已存在");
        }
    }

    @Override
    public void update(RealestateDTO request) {
        updateCheck(request);
        HomeAutoRealestate realestate = BeanUtil.mapperBean(request,HomeAutoRealestate.class);
        buildPath(realestate);
        updateById(realestate);
    }



    @Override
    public BasePageVO<RealestateVO>  page(RealestateQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
//        buildQryPath(request);
        List<RealestateVO> result = this.baseMapper.page(request);
        if (CollectionUtils.isEmpty(result)){
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            BasePageVO<RealestateVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
            return resultData;
        }
        List<Long> realesIds = result.stream().map(RealestateVO::getId).collect(Collectors.toList());
        Map<Long,Integer> countMap = iHomeAutoProjectService.countByRealestateIds(realesIds);
        List<HomeAutoProject> projects= iHomeAutoProjectService.list(new LambdaQueryWrapper<HomeAutoProject>().in(HomeAutoProject::getRealestateId,realesIds).select(HomeAutoProject::getId,HomeAutoProject::getName,HomeAutoProject::getType,HomeAutoProject::getRealestateId,HomeAutoProject::getStatus));
        Map<Long,List<ProjectBaseInfoVO>> maps = null;
        if (!CollectionUtils.isEmpty(projects)){
            List<ProjectBaseInfoVO> projectVOs = BeanUtil.mapperList(projects,ProjectBaseInfoVO.class);
            maps = projectVOs.stream().collect(Collectors.groupingBy(ProjectBaseInfoVO::getRealestateId));
        }
        Map<Long, List<ProjectBaseInfoVO>> finalMaps = maps;
        result.forEach(obj->{
            Integer count = countMap.get(obj.getId());
            if (count == null){
                obj.setProjectCount(0);
            }else {
                obj.setProjectCount(count);
            }
            if (!CollectionUtils.isEmpty(finalMaps) && finalMaps.get(obj.getId()) != null){
                obj.setProjects(finalMaps.get(obj.getId()));
            }
        });
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<RealestateVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }

    /**
     * 构造查询的path
     * @param request
     */
    private void buildQryPath(RealestateQryDTO request) {
        List<String> path = commonService.getUserPathScope();
//        List<String> path =  Lists.newArrayListWithCapacity(3);
//        path.add("CN/110100/110000/110102/efa461cf7f094ed49ff4d469e9819189");
//        path.add("CN/120100/120000/120101/73030189c4894b55ba32ba7c13e5d061/123123");
//        path.add("CN/110100/110000/110102");
//        path.forEach(aa->{
//            log.info("getUserPathScope：{}",aa);
//        });
        if (CollectionUtils.isEmpty(path)){
            return;
        }
        List<String> queryPath = Lists.newArrayListWithCapacity(path.size());
        path.forEach(obj->{
            String[] paths = obj.split("/");
            if (paths.length > 5){
                StringBuilder sb = new StringBuilder();
                String pathstr = sb.append(paths[0]).append("/").append(paths[1]).append("/").append(paths[2]).append("/").append(paths[3]).append("/").append(paths[4]).toString();
                queryPath.add(pathstr);
            }else {
                queryPath.add(obj);
            }
        });
//        request.setPaths(queryPath);
    }

    /**
     * 构造查询的path
     */
    private List<String> buildQryPath() {
        List<String> path = commonService.getUserPathScope();

        if (CollectionUtils.isEmpty(path)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> queryPath = Lists.newArrayListWithCapacity(path.size());
        path.forEach(obj->{
            String[] paths = obj.split("/");
            if (paths.length > 5){
                StringBuilder sb = new StringBuilder();
                String pathstr = sb.append(paths[0]).append("/").append(paths[1]).append("/").append(paths[2]).append("/").append(paths[3]).append("/").append(paths[4]).toString();
                queryPath.add(pathstr);
            }else {
                queryPath.add(obj);
            }
        });
        return queryPath;
    }

    @Override
    public void deleteById(Long id) {
        int count = iHomeAutoProjectService.count(new LambdaQueryWrapper<HomeAutoProject>().eq(HomeAutoProject::getRealestateId,id));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼盘现有项目不可删除");
        }
        removeById(id);
    }

    @Override
    public List<SelectedLongVO> ListSelects() {
        List<HomeAutoRealestate> realestates = list(new LambdaQueryWrapper<HomeAutoRealestate>().select(HomeAutoRealestate::getId,HomeAutoRealestate::getName));
        if (CollectionUtils.isEmpty(realestates)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedLongVO> selectedVOS = Lists.newArrayListWithCapacity(realestates.size());
        realestates.forEach(obj->{
            SelectedLongVO cascadeVo = new SelectedLongVO(obj.getName(), obj.getId());
            selectedVOS.add(cascadeVo);
        });

        return selectedVOS;
    }

    @Override
    public RealestateDeveloperVO getDeveloperInfoById(Long id) {
        RealestateDeveloperVO developerVO = this.baseMapper.getDeveloperInfoById(id);
        return developerVO;
    }



    @Override
    public List<SelectedLongVO> getListSeclectsByUser() {
        List<String> path = buildQryPath();
        return this.baseMapper.getListSeclects(path);
    }

    @Override
    public String getRealestateCodeById(Long realestateId) {
        return this.baseMapper.getRealestateCodeById(realestateId);
    }

    @Override
    public PathBO getRealestatePathInfoById(Long realestateId) {
        return this.baseMapper.getRealestatePathInfoById(realestateId);
    }

    @Override
    public List<CascadeLongVo> getListCascadeSeclects(List<Long> ids) {
        List<CascadeLongVo> data = this.baseMapper.getListCascadeSeclects(ids);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithCapacity(0);
        }
        return data;
    }

    @Override
    public BasePageVO<RealestateModeStatusVO> getListSeclectsByProject(RealestateModeQryDTO request) {
        List<String> ids = iHomeAutoProjectService.getRealestateIdsByfreed(ProjectTypeEnum.ZIYOU.getType());
        PageInfo pageInfo = null;
        if (CollectionUtils.isEmpty(ids)){
            pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            BasePageVO<RealestateModeStatusVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
            return resultData;
        }
        request.setIds(ids);
        List<RealestateModeStatusVO> data = this.baseMapper.getListSeclectsByProject(request);
        pageInfo = new PageInfo(data);
        BasePageVO<RealestateModeStatusVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }

    @Override
    public List<SelectedVO> getModeStatusSeclects() {
        List<SelectedVO> result = Lists.newArrayListWithExpectedSize(EnergyModeEnum.values().length);
        for (EnergyModeEnum value : EnergyModeEnum.values()) {
            SelectedVO selectedVO = new SelectedVO(value.getDesc(),value.getCode());
            result.add(selectedVO);
        }
        return result;
    }

    @Override
    public void updateModeStatus(RealestateModeUpdateVO request) {
        HomeAutoRealestate realestate = BeanUtil.mapperBean(request,HomeAutoRealestate.class);
        updateById(realestate);
    }

    @Override
    public List<CascadeLongVo> cascadeRealestateProject(String name) {
        return this.baseMapper.cascadeRealestateProject(name);
    }

    @Override
    public List<CascadeLongVo> cascadeRealestateProjectBuild(String name) {
        List<CascadeLongVo> data = this.cascadeRealestateProject(name);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<CascadeLongVo> result = Lists.newArrayListWithCapacity(data.size());
        data.forEach(obj->{
            List<CascadeLongVo> projects = obj.getChildren();
            if (!CollectionUtils.isEmpty(projects)){
                List<CascadeLongVo> projectsData = Lists.newArrayListWithCapacity(projects.size());
                projects.forEach(project->{
                    List<String> buildList = iHomeAutoFamilyService.getListBuildByProjectId(project.getValue());
                    if (!CollectionUtils.isEmpty(buildList)){
                        List<CascadeVo> builds = buildList.stream().map(o->{
                            return new CascadeVo(o.concat("栋"),o);
                        }).collect(Collectors.toList());
                        project.setChildren(builds);
                        projectsData.add(project);
                    }
                });
                //项目下有楼栋下才保留
                if (!CollectionUtils.isEmpty(projectsData)){
                    obj.setChildren(projectsData);
                    result.add(obj);
                }
            }
        });
        return result;
    }

    @Override
    public List<CascadeLongVo> cascadeRealestateProjectFamily(Long realestateId, Long projectId) {
        List<CascadeStringVo> data = this.baseMapper.cascadeRealestateProjectFamily(realestateId,projectId);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        HomeAutoRealestate realestate = getById(realestateId);
        HomeAutoProject project = iHomeAutoProjectService.getById(projectId);

        List<CascadeLongVo> result = Lists.newArrayList();
        List<CascadeLongVo> projects = Lists.newArrayListWithCapacity(1);
        CascadeLongVo realestateVO = CascadeLongVo.builder().value(realestate.getId()).label(realestate.getName()).build();
        CascadeLongVo projectVo = CascadeLongVo.builder().value(project.getId()).label(project.getName()).build();
        projectVo.setChildren(data);
        projects.add(projectVo);
        realestateVO.setChildren(projects);
        result.add(realestateVO);
        return result;
    }

    @Override
    public List<CascadeStringVo> cascadeRealestateFamilyRoom(Long realestateId) {
        List<CascadeStringVo> data = this.baseMapper.cascadeRealestateFamilyRoom(realestateId,null);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }


    private void updateCheck(RealestateDTO request) {
        HomeAutoRealestate realestate = getById(request.getId());
        if (realestate == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        if (request.getName().equals(realestate.getName())){
            return;
        }
        addcheck(request);
    }
}
