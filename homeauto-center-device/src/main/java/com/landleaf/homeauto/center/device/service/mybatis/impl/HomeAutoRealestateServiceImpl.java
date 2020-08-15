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
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoProject;
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.enums.realestate.RealestateStatusEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
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

    @Override
    public void add(RealestateDTO request) {
        addcheck(request);
        HomeAutoRealestate realestate = BeanUtil.mapperBean(request,HomeAutoRealestate.class);
        String[]  path = realestate.getPath().split("/");
        if (path == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "地址格式不对");
        }
        int num = iRealestateNumProducerService.getNum(path[path.length-1]);
        String numStr =  buildNumStr(path[path.length-1],num);
        realestate.setCode(numStr);
        realestate.setPathName(realestate.getPathName().concat("/").concat(realestate.getAddress()));
        save(realestate);

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
        realestate.setPathName(realestate.getPathName().concat("/").concat(realestate.getAddress()));
        updateById(realestate);
    }



    @Override
    public BasePageVO<RealestateVO>  page(RealestateQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoRealestate> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getName())){
            wrapper.like(HomeAutoRealestate::getAddress,request.getName());
        }
        wrapper.orderByDesc(HomeAutoRealestate::getCreateTime);
        List<HomeAutoRealestate> realestates = list(wrapper);
        if (CollectionUtils.isEmpty(realestates)){
            return new BasePageVO();
        }
        List<String> realesIds = realestates.stream().map(HomeAutoRealestate::getId).collect(Collectors.toList());
        List<RealestateVO> result = BeanUtil.mapperList(realestates,RealestateVO.class);
        List<String> ids = realestates.stream().map(HomeAutoRealestate::getId).collect(Collectors.toList());
        Map<String,Integer> countMap = iHomeAutoProjectService.countByRealestateIds(ids);
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
        HomeAutoRealestate realestate = getOne(new LambdaQueryWrapper<HomeAutoRealestate>().eq(HomeAutoRealestate::getId,id).select(HomeAutoRealestate::getDeveloperName,HomeAutoRealestate::getPathName));
        RealestateDeveloperVO developerVO = BeanUtil.mapperBean(realestate,RealestateDeveloperVO.class);
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
