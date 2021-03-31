package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrInfo;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolInfo;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolDTO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolQryDTO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolVO;
import com.landleaf.homeauto.center.device.model.mapper.ProtocolInfoMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyPageVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProjectService;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProtocolInfoService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 协议表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ProtocolInfoServiceImpl extends ServiceImpl<ProtocolInfoMapper, ProtocolInfo> implements IProtocolInfoService {

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;
    @Autowired
    private IProtocolAttrInfoService iProtocolAttrInfoService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;

    @Override
    public ProtocolInfo addProtocol(ProtocolDTO requestData) {
        addcheck(requestData);
        ProtocolInfo protocolInfo = BeanUtil.mapperBean(requestData,ProtocolInfo.class);
        save(protocolInfo);
        return protocolInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProtocol(ProtocolDTO requestData) {
        ProtocolInfo protocol = getById(requestData.getId());
        if (Objects.isNull(protocol)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        updateCheck(requestData,protocol);
        ProtocolInfo protocolInfo = BeanUtil.mapperBean(requestData,ProtocolInfo.class);
        updateById(protocolInfo);
        if (!protocol.getCode().equals(requestData.getCode())){
            List<ProtocolAttrInfo> attrInfos = iProtocolAttrInfoService.list(new LambdaQueryWrapper<ProtocolAttrInfo>().eq(ProtocolAttrInfo::getProtocolId,requestData.getId()).select(ProtocolAttrInfo::getId,ProtocolAttrInfo::getCode));
            if (CollectionUtils.isEmpty(attrInfos)){
                return;
            }
            attrInfos.forEach(o->{
                if(!StringUtil.isEmpty(o.getCode())){
                    o.setCode(o.getCode().replaceFirst(protocol.getCode(),requestData.getCode()));
                }
            });
            iProtocolAttrInfoService.updateBatchById(attrInfos);
        }
    }

    private void updateCheck(ProtocolDTO requestData,ProtocolInfo protocolInfo) {
        if (requestData.getCode().equals(protocolInfo.getCode()) && requestData.getName().equals(protocolInfo.getName())){
            return;
        }
        if (!requestData.getCode().equals(protocolInfo.getCode()) && !requestData.getName().equals(protocolInfo.getName())){
            protocolCheck(requestData.getCode(),requestData.getName());
        }else if (!requestData.getCode().equals(protocolInfo.getCode())){
            protocolCheck(requestData.getCode(),null);
        }else if (!requestData.getName().equals(protocolInfo.getName())){
            protocolCheck(null,requestData.getName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProtocolById(String protocolId) {
        int count = 0;
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该协议有产品关联不可删除");
        }
        int count2 = iHomeAutoProjectService.count(new LambdaQueryWrapper<HomeAutoProject>().eq(HomeAutoProject::getProtocolHvacId,protocolId).or().eq(HomeAutoProject::getProtocolAutoId,protocolId).last("limit 1"));
        if (count2 > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该协议有项目关联不可删除");
        }
        removeById(protocolId);
        iProtocolAttrInfoService.deleteProtocolAttrByProtocolId(protocolId);

    }

    @Override
    public BasePageVO<ProtocolVO> getListProtocol(ProtocolQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<ProtocolInfo> protocols = list(new LambdaQueryWrapper<ProtocolInfo>().select(ProtocolInfo::getCode,ProtocolInfo::getName,ProtocolInfo::getId,ProtocolInfo::getType).orderByDesc(ProtocolInfo::getCreateTime));
        if (CollectionUtils.isEmpty(protocols)){
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }
        PageInfo pageInfo = new PageInfo(protocols);
        List<ProtocolVO> protocolVOS = BeanUtil.mapperList(protocols,ProtocolVO.class);
        BasePageVO<ProtocolVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        resultData.setList(protocolVOS);
        return resultData;
    }



    @Override
    public List<SelectedVO> getListSelectProtocol(Integer type) {
        LambdaQueryWrapper<ProtocolInfo> wrapper = new LambdaQueryWrapper<ProtocolInfo>().select(ProtocolInfo::getId,ProtocolInfo::getName,ProtocolInfo::getCode);
        if (Objects.nonNull(type)){
            wrapper.eq(ProtocolInfo::getType,type);
        }
        List<ProtocolInfo> protocolInfos = list(wrapper);
        if (CollectionUtils.isEmpty(protocolInfos)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(protocolInfos.size());
        protocolInfos.forEach(protocol->{
            StringBuilder sb = new StringBuilder();
            sb.append(protocol.getName()).append("-").append(protocol.getCode());
            selectedVOS.add(new SelectedVO(sb.toString(),protocol.getId()));
        });
        return selectedVOS;
    }


    private void addcheck(ProtocolDTO requestData) {
        protocolCheck(requestData.getCode(),requestData.getName());
    }

    private void protocolCheck(String code, String name) {
        LambdaQueryWrapper<ProtocolInfo> wrapper = new LambdaQueryWrapper();
        if (!StringUtil.isEmpty(code)){
            wrapper.eq(ProtocolInfo::getCode,code);
        }
        if (!StringUtil.isEmpty(name)){
            wrapper.or().eq(ProtocolInfo::getName,name);
        }
        int count = count(wrapper);
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "名称或标志已存在");
        }
    }
}
