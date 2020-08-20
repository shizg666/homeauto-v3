package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoRealestateMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProjectService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoRealestateService;
import com.landleaf.homeauto.center.device.service.mybatis.IRealestateNumProducerService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.enums.realestate.RealestateStatusEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
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
public class HomeAutoRealestateServiceImpl extends ServiceImpl<HomeAutoRealestateMapper, HomeAutoRealestate> implements IHomeAutoRealestateService {
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;
    @Autowired
    private IRealestateNumProducerService iRealestateNumProducerService;
    @Autowired
    private CommonServiceImpl commonService;

    @Override
    public void add(RealestateDTO request) {
        addcheck(request);
        // CN/610000/610100/610112/f4f711c4e9724f4b978a2d698ecbaf7f
        // 中国/陕西省/西安市/未央区/上实
        HomeAutoRealestate realestate = BeanUtil.mapperBean(request,HomeAutoRealestate.class);
        buildPath(realestate);
        int num = iRealestateNumProducerService.getNum(realestate.getAreaCode());
        String numStr =  buildNumStr(realestate.getAreaCode(),num);
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
        request.setId(IdGeneratorUtil.getUUID32());
        request.setPathOauth(request.getPath().concat("/").concat(request.getId()));
        request.setProvinceCode(path[1]);
        request.setCityCode(path[2]);
        request.setAreaCode(path[3]);
        request.setCountryCode(path[0]);

        request.setProvince(pathName[1]);
        request.setCity(pathName[2]);
        request.setArea(pathName[3]);
        request.setCountry(pathName[0]);
    }

    /**
     * 楼盘编号构建行政区编码+4数字
     * @param s
     * @param num
     * @return
     */
    private String buildNumStr(String s, int num) {
        String str = s;
        if (num <10){
            str = s.concat("0").concat(String.valueOf(num));
        }else if (num <100){
            str = String.valueOf(num);
        }else{
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "编码过大请联系管理员");
        }
        return str;
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

        buildQryPath(request);

        List<RealestateVO> result = this.baseMapper.page(request);
        if (CollectionUtils.isEmpty(result)){
            return new BasePageVO();
        }
        List<String> realesIds = result.stream().map(RealestateVO::getId).collect(Collectors.toList());
        Map<String,Integer> countMap = iHomeAutoProjectService.countByRealestateIds(realesIds);
        List<HomeAutoProject> projects= iHomeAutoProjectService.list(new LambdaQueryWrapper<HomeAutoProject>().in(HomeAutoProject::getRealestateId,realesIds).select(HomeAutoProject::getName,HomeAutoProject::getType,HomeAutoProject::getRealestateId));
        Map<String,List<ProjectBaseInfoVO>> maps = null;
        if (!CollectionUtils.isEmpty(projects)){
            List<ProjectBaseInfoVO> projectVOs = BeanUtil.mapperList(projects,ProjectBaseInfoVO.class);
            maps = projectVOs.stream().collect(Collectors.groupingBy(ProjectBaseInfoVO::getRealestateId));
        }
        Map<String, List<ProjectBaseInfoVO>> finalMaps = maps;
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
        request.setPaths(queryPath);
    }

    @Override
    public void deleteById(String id) {
        int count = iHomeAutoProjectService.count(new LambdaQueryWrapper<HomeAutoProject>().eq(HomeAutoProject::getRealestateId,id));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼盘现有项目不可删除");
        }
        removeById(id);
    }

    @Override
    public List<SelectedVO> ListSelects() {
        List<HomeAutoRealestate> realestates = list(new LambdaQueryWrapper<HomeAutoRealestate>().select(HomeAutoRealestate::getId,HomeAutoRealestate::getName));
        if (CollectionUtils.isEmpty(realestates)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(realestates.size());
        realestates.forEach(obj->{
            SelectedVO cascadeVo = new SelectedVO(obj.getName(), obj.getId());
            selectedVOS.add(cascadeVo);
        });

        return selectedVOS;
    }

    @Override
    public RealestateDeveloperVO getDeveloperInfoById(String id) {
        RealestateDeveloperVO developerVO = this.baseMapper.getDeveloperInfoById(id);
        return developerVO;
    }

    @Override
    public List<SelectedIntegerVO> getRealestateStatus() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (RealestateStatusEnum value : RealestateStatusEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
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
