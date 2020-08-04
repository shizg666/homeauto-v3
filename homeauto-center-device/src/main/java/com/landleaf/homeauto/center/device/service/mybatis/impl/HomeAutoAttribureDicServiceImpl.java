package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoAttribureDicMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttribureDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeInfoDicService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttribureDic;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttributeInfoDic;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeInfoDicDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        HomeAutoAttribureDic autoAttribureDic = BeanUtil.mapperBean(request,HomeAutoAttribureDic.class);
        save(autoAttribureDic);
        List<AttributeInfoDicDTO> infos = request.getInfos();
        List<HomeAutoAttributeInfoDic> attributeInfoDicDTOS = BeanUtil.mapperList(infos, HomeAutoAttributeInfoDic.class);
        iHomeAutoAttributeInfoDicService.saveBatch(attributeInfoDicDTOS);
    }

    @Override
    public void update(AttribureDicDTO request) {
        HomeAutoAttribureDic autoAttribureDic = BeanUtil.mapperBean(request,HomeAutoAttribureDic.class);
        updateById(autoAttribureDic);

    }
}
