package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.CategoryAttribute;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoAttributeDic;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoAttributeInfoDic;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAttribureDicMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ICategoryAttributeService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeInfoDicService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性字典表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Service
public class HomeAutoAttributeDicServiceImpl extends ServiceImpl<HomeAutoAttribureDicMapper, HomeAutoAttributeDic> implements IHomeAutoAttributeDicService {

    @Autowired
    private IHomeAutoAttributeInfoDicService iHomeAutoAttributeInfoDicService;
    @Autowired
    private ICategoryAttributeService iCategoryAttributeService;
    @Autowired
    private IdService idservice;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AttributeDicDTO request) {
        checkParam(request);
        HomeAutoAttributeDic autoAttribureDic = BeanUtil.mapperBean(request, HomeAutoAttributeDic.class);
        save(autoAttribureDic);
        request.setId(autoAttribureDic.getId());
        saveAttributeInfo(request);

    }

    private void saveAttributeInfo(AttributeDicDTO request) {
        List<AttributeInfoDicDTO> infos = request.getInfos();
        if (CollectionUtils.isEmpty(infos)) {
            return;
        }
        Set<String> codes = infos.stream().map(AttributeInfoDicDTO::getCode).collect(Collectors.toSet());
        if (infos.size() != codes.size()) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "属性值code不可重复");
        }
        List<HomeAutoAttributeInfoDic> attributeInfoDics = BeanUtil.mapperList(infos, HomeAutoAttributeInfoDic.class);
        Long id = request.getId();
        attributeInfoDics.forEach(obj -> {
            obj.setAttributeId(id);
        });
        iHomeAutoAttributeInfoDicService.saveBatch(attributeInfoDics);
    }

    private void checkParam(AttributeDicDTO request) {
//        int count = count(new LambdaQueryWrapper<HomeAutoAttributeDic>().eq(HomeAutoAttributeDic::getCode, request.getCode()).or().eq(HomeAutoAttributeDic::getName, request.getName()));
        int count = count(new LambdaQueryWrapper<HomeAutoAttributeDic>().eq(HomeAutoAttributeDic::getCode, request.getCode()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "属性code已存在");
        }
    }

    private void updateCheckParam(AttributeDicDTO request) {
        HomeAutoAttributeDic attributeDic = getById(request.getId());
        if (request.getCode().equals(attributeDic.getCode())) {
            return;
        }
        checkParam(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AttributeDicDTO request) {
        updateCheckParam(request);
        HomeAutoAttributeDic autoAttribureDic = BeanUtil.mapperBean(request, HomeAutoAttributeDic.class);
        updateById(autoAttribureDic);
        iHomeAutoAttributeInfoDicService.remove(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId, request.getId()));
        saveAttributeInfo(request);
    }

    @Override
    public BasePageVO<AttributeDicPageVO> pageList(AttributeDicQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoAttributeDic> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getCode())) {
            queryWrapper.like(HomeAutoAttributeDic::getCode, request.getCode());
        }
        if (!StringUtil.isEmpty(request.getName())) {
            queryWrapper.like(HomeAutoAttributeDic::getName, request.getName());
        }
        if (request.getNature() != null) {
            queryWrapper.eq(HomeAutoAttributeDic::getNature, request.getNature());
        }
        queryWrapper.select(HomeAutoAttributeDic::getId, HomeAutoAttributeDic::getName, HomeAutoAttributeDic::getNature,HomeAutoAttributeDic::getCode);
        queryWrapper.orderByDesc(HomeAutoAttributeDic::getCreateTime);
        List<HomeAutoAttributeDic> resultList = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(resultList);
        List<AttributeDicPageVO> result = BeanUtil.mapperList(resultList, AttributeDicPageVO.class);
        pageInfo.setList(result);
        BasePageVO<AttributeDicPageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public AttributeDicDetailVO getAttrDetailByCode(String code) {
        AttributeDicDetailVO data = this.getBaseMapper().getInfoByCode(code);
        if (data == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "code不存在");
        }
        List<HomeAutoAttributeInfoDic> infoData = iHomeAutoAttributeInfoDicService.getBaseMapper().selectList(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId, data.getId()).select(HomeAutoAttributeInfoDic::getCode, HomeAutoAttributeInfoDic::getName, HomeAutoAttributeInfoDic::getSortNo, HomeAutoAttributeInfoDic::getId).orderByAsc(HomeAutoAttributeInfoDic::getSortNo));
        List<AttributeInfoDicDTO> infoDicDTOS = BeanUtil.mapperList(infoData, AttributeInfoDicDTO.class);
        data.setInfos(infoDicDTOS);
        return data;
    }


    @Override
    public AttributeDicDetailVO getDetailById(String id) {
        AttributeDicDetailVO data = this.getBaseMapper().getInfoById(id);
        if (data == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        List<HomeAutoAttributeInfoDic> infoData = iHomeAutoAttributeInfoDicService.getBaseMapper().selectList(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId, id).select(HomeAutoAttributeInfoDic::getCode, HomeAutoAttributeInfoDic::getName, HomeAutoAttributeInfoDic::getSortNo, HomeAutoAttributeInfoDic::getId).orderByAsc(HomeAutoAttributeInfoDic::getSortNo));
        if (CollectionUtils.isEmpty(infoData)){
            return data;
        }
        List<AttributeInfoDicDTO> infoDicDTOS = BeanUtil.mapperList(infoData, AttributeInfoDicDTO.class);
        data.setInfos(infoDicDTOS);
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        int count = iCategoryAttributeService.count(new LambdaQueryWrapper<CategoryAttribute>().eq(CategoryAttribute::getAttributeId, id));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "属性值已被品类引用不可删除");
        }
        this.removeById(id);
        iHomeAutoAttributeInfoDicService.remove(new LambdaQueryWrapper<HomeAutoAttributeInfoDic>().eq(HomeAutoAttributeInfoDic::getAttributeId, id));
    }

    @Override
    public List<SelectedIntegerVO> getAttributeDicTypes() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (AttributeTypeEnum value : AttributeTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedIntegerVO> getAttributeDicNatures() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (AttributeNatureEnum value : AttributeNatureEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getAttributes() {
        List<HomeAutoAttributeDic> attribureDics = list(new LambdaQueryWrapper<HomeAutoAttributeDic>().select(HomeAutoAttributeDic::getCode, HomeAutoAttributeDic::getName));
        if (CollectionUtils.isEmpty(attribureDics)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(attribureDics.size());
        attribureDics.forEach(obj -> {
            SelectedVO cascadeVo = new SelectedVO(obj.getName(), String.valueOf(obj.getCode()));
            selectedVOS.add(cascadeVo);
        });
        return selectedVOS;
    }

    @Override
    public AttributeCascadeVO getCascadeInfoByCode(String code) {
        AttributeDicDetailVO dicDetailVO = getAttrDetailByCode(code);
        if (dicDetailVO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "品类code不存在");
        }
        AttributeCascadeVO result = BeanUtil.mapperBean(dicDetailVO, AttributeCascadeVO.class);
        if (CollectionUtils.isEmpty(result.getInfos())) {
            return result;
        }
        return result;
    }

    @Override
    public List<AttributeDicVO> getListAttributes() {
        return this.baseMapper.getListAttributes();
    }




}
