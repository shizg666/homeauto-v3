package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.SobotTicketTypeFieldMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketTypeFieldService;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeField;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class SobotTicketTypeFieldServiceImpl extends ServiceImpl<SobotTicketTypeFieldMapper, SobotTicketTypeField> implements ISobotTicketTypeFieldService {

    @Override
    public String getRepirFieldId(String typeId, String attributeCode) {

        QueryWrapper<SobotTicketTypeField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("typeid", typeId);
        queryWrapper.eq("attribute_code", attributeCode);
        return getOne(queryWrapper).getFieldid();
    }

    @Override
    public List<SobotTicketTypeField> getFieldByTypeId(String typeid) {
        QueryWrapper<SobotTicketTypeField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("typeid", typeid);
        return list(queryWrapper);
    }
}
