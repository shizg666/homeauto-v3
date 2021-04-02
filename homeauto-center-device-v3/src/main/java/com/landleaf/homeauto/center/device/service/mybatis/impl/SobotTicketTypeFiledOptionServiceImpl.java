package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.SobotTicketTypeFiledOptionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketTypeFiledOptionService;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 智齿客服平台-工单分类-自定义字段-可选项 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class SobotTicketTypeFiledOptionServiceImpl extends ServiceImpl<SobotTicketTypeFiledOptionMapper, SobotTicketTypeFiledOption> implements ISobotTicketTypeFiledOptionService {

    @Override
    public void saveOptionData(List<SobotTicketTypeFiledOption> saveDatas) {
        QueryWrapper<SobotTicketTypeFiledOption> queryWrapper = new QueryWrapper<>();
        remove(queryWrapper);
        saveBatch(saveDatas);
    }

    @Override
    public List<SobotTicketTypeFiledOption> getByFieldid(String repirFieldId) {
        QueryWrapper<SobotTicketTypeFiledOption> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fieldid",repirFieldId);
        return list(queryWrapper);
    }
}
