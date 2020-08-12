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
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttributeDic;
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoProject;
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryPageVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDeveloperVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
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
        realestate.setNum(numStr);
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
            str = s.concat("000").concat(String.valueOf(num));
        }else if (num <100){
            str = s.concat("00").concat(String.valueOf(num));
        }else if (num <1000){
            str = s.concat("0").concat(String.valueOf(num));
        }else{
            str = s.concat(String.valueOf(num));
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
        if (!StringUtil.isEmpty(request.getAddress())){
            wrapper.like(HomeAutoRealestate::getAddress,request.getAddress());
        }
        if (!StringUtil.isEmpty(request.getPath())){
            wrapper.like(HomeAutoRealestate::getPath,request.getPath());
        }
        List<HomeAutoRealestate> realestates = list(wrapper);
        if (CollectionUtils.isEmpty(realestates)){
            return new BasePageVO();
        }
        List<RealestateVO> result = BeanUtil.mapperList(realestates,RealestateVO.class);
        List<String> ids = realestates.stream().map(HomeAutoRealestate::getId).collect(Collectors.toList());
        Map<String,Integer> countMap = iHomeAutoProjectService.countByRealestateIds(ids);
        result.forEach(obj->{
            Integer count = countMap.get(obj.getId());
            if (count == null){
                obj.setProjectCount(0);
            }else {
                obj.setProjectCount(count);
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
