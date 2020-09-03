package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.AdapterRequestMsgLog;
import com.landleaf.homeauto.center.device.model.mapper.AdapterRequestMsgLogMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 控制命令操作日志 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Service
public class AdapterRequestMsgLogServiceImpl extends ServiceImpl<AdapterRequestMsgLogMapper, AdapterRequestMsgLog> implements IAdapterRequestMsgLogService {

    @Async(value = "adapterRequestMsgLogExecute")
    @Override
    public void saveRecord(AdapterMessageBaseDTO message, String content) {

        AdapterRequestMsgLog saveData = new AdapterRequestMsgLog();
        BeanUtils.copyProperties(message, saveData);
        saveData.setContent(content);
        save(saveData);
    }

    @Async(value = "adapterRequestMsgLogExecute")
    @Override
    public void updatRecord(AdapterMessageAckDTO message) {

        QueryWrapper<AdapterRequestMsgLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", message.getMessageId());
        queryWrapper.eq("family_id", message.getFamilyId());
        queryWrapper.orderByDesc("create_time");
        List<AdapterRequestMsgLog> list = list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            AdapterRequestMsgLog updateLog = list.get(0);
            updateLog.setCode(message.getCode());
            updateLog.setMessage(message.getMessage());
            updateById(updateLog);
        }
    }
}
