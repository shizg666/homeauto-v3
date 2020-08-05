package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAttribureDicMapper;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttribureDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeInfoDicService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttribureDic;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttributeInfoDic;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 属性字典表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Service
public class HomeAutoAttribureDicServiceImpl extends ServiceImpl<HomeAutoAttribureDicMapper, HomeAutoAttribureDic> implements IHomeAutoAttribureDicService {


    @Autowired
    private IHomeAutoAttributeInfoDicService iHomeAutoAttributeInfoDicService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AttribureDicDTO request) {
        CheckParam(request);
        HomeAutoAttribureDic autoAttribureDic = BeanUtil.mapperBean(request,HomeAutoAttribureDic.class);
        save(autoAttribureDic);
        List<AttributeInfoDicDTO> infos = request.getInfos();
        if (!CollectionUtils.isEmpty(infos)){
            List<HomeAutoAttributeInfoDic> attributeInfoDics = BeanUtil.mapperList(infos, HomeAutoAttributeInfoDic.class);
            String id = autoAttribureDic.getId();
            attributeInfoDics.forEach(obj->{
                obj.setAttributeId(id);
            });
            iHomeAutoAttributeInfoDicService.saveBatch(attributeInfoDics);
        }
    }

    private void CheckParam(AttribureDicDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoAttribureDic>().eq(HomeAutoAttribureDic::getName,request.getName()).or().eq(HomeAutoAttribureDic::getCode,request.getCode()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "属性名称/code已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AttribureDicDTO request) {
        CheckParam(request);
        HomeAutoAttribureDic autoAttribureDic = BeanUtil.mapperBean(request,HomeAutoAttribureDic.class);
        updateById(autoAttribureDic);
        iHomeAutoAttributeInfoDicService.remove(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId,request.getId()));
        if(!CollectionUtils.isEmpty(request.getInfos())){
            List<HomeAutoAttributeInfoDic> attributeInfoDics = BeanUtil.mapperList(request.getInfos(), HomeAutoAttributeInfoDic.class);
            String id = autoAttribureDic.getId();
            attributeInfoDics.forEach(obj->{
                obj.setAttributeId(id);
            });
            iHomeAutoAttributeInfoDicService.saveBatch(attributeInfoDics);
        }
    }

    @Override
    public BasePageVO<AttribureDicPageVO> pageList(AttribureDicQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoAttribureDic> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getName())){
            queryWrapper.like(HomeAutoAttribureDic::getName,request.getName());
        }
        if (request.getNature() != null){
            queryWrapper.eq(HomeAutoAttribureDic::getNature,request.getNature());
        }
        queryWrapper.select(HomeAutoAttribureDic::getId,HomeAutoAttribureDic::getName,HomeAutoAttribureDic::getNature);
        List<HomeAutoAttribureDic> resultList = list(queryWrapper);
        List<AttribureDicPageVO> result = BeanUtil.mapperList(resultList,AttribureDicPageVO.class);
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<AttribureDicPageVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }

    @Override
    public AttribureDicDetailVO getDetailById(String id) {
        AttribureDicDetailVO data = this.getBaseMapper().getInfoById(id);
        List<HomeAutoAttributeInfoDic> infoData = iHomeAutoAttributeInfoDicService.getBaseMapper().selectList(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId,id).select(HomeAutoAttributeInfoDic::getCode,HomeAutoAttributeInfoDic::getName,HomeAutoAttributeInfoDic::getOrderNum,HomeAutoAttributeInfoDic::getId).orderByAsc(HomeAutoAttributeInfoDic::getOrderNum));
        List<AttributeInfoDicDTO> infoDicDTOS = BeanUtil.mapperList(infoData,AttributeInfoDicDTO.class);
        data.setInfos(infoDicDTOS);
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
       this.removeById(id);
       iHomeAutoAttributeInfoDicService.remove(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId,id));
    }

    @Override
    public List<SelectedVO> getAttributeDicTypes() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (AttributeTypeEnum value : AttributeTypeEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), String.valueOf(value.getType()));
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getAttributeDicNatures() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (AttributeNatureEnum value : AttributeNatureEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), String.valueOf(value.getType()));
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }
}
